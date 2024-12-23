package io.github.livenlearnaday.mytictactoe.history

import MyTicTacToeTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord

@Composable
fun HistoryListItem(
    record: GameRecord,
    modifier: Modifier = Modifier,
    onRecordClicked: (gameRecord: GameRecord) -> Unit,
    onDeleteIconClicked: (gameRecord: GameRecord) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onRecordClicked(record) }
    ) {
        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                Text(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(horizontal = 20.dp),
                    maxLines = 2,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    text = record.fileName,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(horizontal = 20.dp),
                    maxLines = 2,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    text = record.winner,
                    color = Color.Black
                )

                IconButton(
                    onClick = {
                        onDeleteIconClicked(record)
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun PreviewMovieListItem() {
    MyTicTacToeTheme {
        HistoryListItem (
            record = (
                GameRecord(
                    id = 1,
                    fileName = "http://path",
                    winner = Player.X.toString()
                )
            ),
            onRecordClicked = {},
            onDeleteIconClicked = {}
        )
    }
}