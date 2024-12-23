package io.github.livenlearnaday.mytictactoe.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.mytictactoe.data.model.Cell
import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.ui.icons.PanoramaFishEye


@Composable
fun CellWidget(cell: Cell, modifier: Modifier = Modifier, onClick: (cell: Cell) -> Unit) {
    Card(
        modifier = modifier
            .clickable {
                onClick(cell)
            }
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (cell.player) {
                Player.X -> {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center)
                    )
                }

                Player.O -> {
                    Icon(
                        imageVector = Icons.Outlined.PanoramaFishEye,
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center)
                    )
                }

                else -> {
//                no op
                }
            }

        }
    }
}
