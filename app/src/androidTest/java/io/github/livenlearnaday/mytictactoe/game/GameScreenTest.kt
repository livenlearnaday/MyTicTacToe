package io.github.livenlearnaday.mytictactoe.game


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.data.model.Winner
import org.junit.Rule
import org.junit.Test

class GameScreenTest {

    @get:Rule
    val rule = createComposeRule()


    @Test
    fun should_show_game_over_text_when_there_is_winner() {
        // Arrange
        rule.setContent {
            GameScreen(
                gameState = GameState(
                    winner = Winner(currentPlayer = Player.O, true)
                ),
                gameViewModel = GameViewModel{},
                onHistoryButtonClicked = { },
                onGameAction = { }
            )
        }

        // Act & Assert
        rule.onNodeWithText("Game Over").assertExists()
    }

}

