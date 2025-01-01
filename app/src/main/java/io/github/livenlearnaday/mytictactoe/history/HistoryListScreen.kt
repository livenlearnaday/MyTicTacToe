package io.github.livenlearnaday.mytictactoe.history

import MyTicTacToeTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.mytictactoe.R
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord

@Composable
fun HistoryListScreen(
   historyState: HistoryState,
   onHistoryListAction: (HistoryListAction) -> Unit,
   onRecordClicked: (record: GameRecord) -> Unit,
   modifier: Modifier = Modifier
) {

   LaunchedEffect (historyState.isRecordsUpdated) {
      onHistoryListAction(HistoryListAction.OnRecordsUpdated)
   }

   LaunchedEffect(Unit) {
      onHistoryListAction(HistoryListAction.OnScreenLaunched)
   }

   Surface {
      Scaffold(
         topBar = {
            TopAppBar(
               title = {
                  Text(
                     modifier = Modifier.fillMaxWidth(),
                     text = stringResource(R.string.game_play_record)
                  )
               }
            )
         },
         content = { paddingValues ->
            Box(modifier = modifier.fillMaxSize()
               .padding(paddingValues),
               contentAlignment = Alignment.TopStart) {

               LazyColumn(
                  modifier = Modifier.testTag("records")
                     .padding(vertical = 4.dp),
                  verticalArrangement = Arrangement.spacedBy(8.dp),
               ) {
                  items(items = historyState.gameRecords) { record ->
                     HistoryListItem (
                        record = record,
                        onRecordClicked = {
                           onRecordClicked(record)
                        },
                        onDeleteIconClicked = {
                           onHistoryListAction(HistoryListAction.OnDeleteById(record))
                        }
                     )
                  }
               }
            }
         }
      )
   }
}

@Composable
@Preview
fun PreviewMovieListScreen() {
   MyTicTacToeTheme {
      HistoryListScreen(
         historyState = HistoryState(
            gameRecords = listOf(
               GameRecord(1, "aaa", "Player X"),
               GameRecord(2, "bbb", "Player Y"),
               GameRecord(3, "ccc", "Player X"),
            )
         ),
         onHistoryListAction = {},
         onRecordClicked = {}
      )

   }
}

