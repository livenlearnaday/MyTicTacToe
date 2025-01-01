package io.github.livenlearnaday.mytictactoe.game

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.history.HistoryListScreen
import io.github.livenlearnaday.mytictactoe.history.HistoryState
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class HistoryListScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun should_show_history_content() {
        // Arrange
        rule.setContent {
            HistoryListScreen(
                historyState = HistoryState( gameRecords = createGameRecords()),
                onHistoryListAction = { },
                onRecordClicked = { }
            )
        }

        // Act & Assert
        rule.onNodeWithTag("records").assertExists()
    }

}

private fun createGameRecords(): List<GameRecord> {
    return sequence<GameRecord> {
        GameRecord(
            fileName = UUID.randomUUID().toString(),
            filePath = UUID.randomUUID().toString(),
            winner = UUID.randomUUID().toString()
        )
    }.take(10).toList()
}