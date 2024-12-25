package io.github.livenlearnaday.mytictactoe.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.DeleteGameRecordByIdUseCase
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.FetchGameRecordsUseCase
import io.github.livenlearnaday.mytictactoe.utils.checkIfFileExist
import io.github.livenlearnaday.mytictactoe.utils.getVideoFilePath
import kotlinx.coroutines.launch
import java.io.File

class HistoryViewModel(
    private val fetchGameRecordsUseCase: FetchGameRecordsUseCase,
    private val deleteGameRecordByIdUseCase: DeleteGameRecordByIdUseCase
) : ViewModel() {

    var historyState by mutableStateOf(HistoryState())
        private set

    init {
        getGameRecordsFromDb()

    }

    fun historyListAction(action: HistoryListAction) {
        when (action) {
            is HistoryListAction.OnDeleteById -> {
                deleteGameRecordById(action.gameRecord)
            }

            is HistoryListAction.OnRecordsUpdated -> {
                getGameRecordsFromDb()

            }
        }
    }


    private fun getGameRecordsFromDb() = viewModelScope.launch {
        fetchGameRecordsUseCase.execute()
            .collect { records ->
                checkSavedFiles(records)
                historyState = historyState.copy(
                    gameRecords = records
                )
            }
    }

    private fun deleteGameRecordById(gameRecord: GameRecord) = viewModelScope.launch {
        deleteGameRecordByIdUseCase.execute(gameRecord.id)
        deleteFileInDevice(gameRecord)

    }

    private fun deleteFileInDevice(record: GameRecord) {
        val filePath = record.fileName.getVideoFilePath
        val file = File(filePath).absoluteFile
        if (record.fileName.checkIfFileExist()) {
            file.delete()
        }
    }

    private fun checkSavedFiles(records: List<GameRecord>) {
        records.forEach { record ->
            checkIfRecordNeedTobeUpdated(record)
        }
    }


    private fun checkIfRecordNeedTobeUpdated(record: GameRecord) {

        var recordsUpdated = 0

        when {
            record.fileName.checkIfFileExist() -> record
            else -> {
                deleteGameRecordById(record)
                recordsUpdated++
            }
        }
        if (recordsUpdated > 0) {
            historyState = historyState.copy(
                isRecordsUpdated = true
            )
        }
    }


}