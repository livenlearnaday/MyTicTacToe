package io.github.livenlearnaday.mytictactoe.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.DeleteGameRecordByIdUseCase
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.FetchGameRecordsUseCase
import io.github.livenlearnaday.mytictactoe.utils.checkIfFileExistByFileName
import io.github.livenlearnaday.mytictactoe.utils.deleteFileByFileName
import kotlinx.coroutines.launch

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
        record.fileName.deleteFileByFileName()
    }

    private fun checkSavedFiles(records: List<GameRecord>) {
        records.forEach { record ->
            checkIfRecordNeedTobeUpdated(record)
        }
    }


    private fun checkIfRecordNeedTobeUpdated(record: GameRecord) {

        var recordsUpdated = 0

        when {
            record.fileName.checkIfFileExistByFileName() -> record
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