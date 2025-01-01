package io.github.livenlearnaday.mytictactoe.game

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.data.enums.UiMode
import io.github.livenlearnaday.mytictactoe.data.model.Cell
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.SaveGameRecordUseCase
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin


@RunWith(AndroidJUnit4::class)
class GameViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val saveGameRecordUseCase: SaveGameRecordUseCase = mockk()

    private lateinit var viewModel: GameViewModel
    private lateinit var mockContext: Context


    @Before
    fun setup(){
        mockContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel = GameViewModel(saveGameRecordUseCase)

    }

    @After
    fun tearDown(){
        stopKoin()
    }

    @Test
    fun should_reset_game(){
        // Arrange
        val gameAction = GameAction.ResetGame
        viewModel.gameAction(GameAction.OnClickCell(Cell(0, 0, Player.X), mockContext))

        // Act
        viewModel.gameAction(gameAction)

        // Assert
        assertEquals(0, viewModel.gameState.moveCount)
        assertEquals(UiMode.NO_ACTION, viewModel.gameState.uiMode)
        assertEquals(false, viewModel.gameState.isShowDialog)

    }

    @Test
    fun should_return_single_player_mode_true(){
        // Arrange
        val gameAction = GameAction.OnClickSinglePlayerMode
        viewModel.gameAction(GameAction.OnClickMultiPlayerMode)

        // Act
        viewModel.gameAction(gameAction)

        // Assert
        assertEquals(true, viewModel.gameState.isSinglePlayer)
    }



}