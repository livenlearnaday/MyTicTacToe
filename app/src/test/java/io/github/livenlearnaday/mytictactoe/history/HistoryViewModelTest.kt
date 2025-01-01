package io.github.livenlearnaday.mytictactoe.history

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.DeleteGameRecordByIdUseCase
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.FetchGameRecordsUseCase
import io.github.livenlearnaday.mytictactoe.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin


@RunWith(AndroidJUnit4::class)
class HistoryViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private val fetchGameRecordsUseCase: FetchGameRecordsUseCase = mockk()
    private val deleteGameRecordByIdUseCase: DeleteGameRecordByIdUseCase = mockk()

    private lateinit var viewModel: HistoryViewModel
    private lateinit var mockContext: Context

    @Before
    fun setup() {
        mockContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel = HistoryViewModel(fetchGameRecordsUseCase, deleteGameRecordByIdUseCase)
    }

    @After
    fun tearDown(){
        stopKoin()
    }


    @Test
    fun should_get_game_records() {
        // Arrange
        val fileName1 = "record1"
        val fileName2 = "record2"
        val gameRecords = listOf(
            GameRecord(
                id = 1,
                fileName = fileName1
            ),
            GameRecord(
                id = 2,
                fileName = fileName2
            )
        )
        val historyListAction = HistoryListAction.OnScreenLaunched
        every { fetchGameRecordsUseCase.execute() } returns flowOf(gameRecords)
        coEvery { deleteGameRecordByIdUseCase.execute(any()) } returns Unit

        // Act
        viewModel.historyListAction(historyListAction)

        // Assert
        assertEquals(gameRecords, viewModel.historyState.gameRecords)

    }

    @Test
    fun should_trigger_delete_record(){
        // Arrange
        val gameRecord = GameRecord(
            id = 2,
            fileName = "file2"
        )
        val historyListAction = HistoryListAction.OnDeleteById(gameRecord)
        coEvery { deleteGameRecordByIdUseCase.execute(any()) } returns Unit

        // Act
        viewModel.historyListAction(historyListAction)

        // Assert
        coVerify { deleteGameRecordByIdUseCase.execute(any()) }
    }






}