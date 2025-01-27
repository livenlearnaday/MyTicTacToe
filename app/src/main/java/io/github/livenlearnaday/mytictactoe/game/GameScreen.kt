package io.github.livenlearnaday.mytictactoe.game

import MyTicTacToeTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.mytictactoe.R
import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.data.enums.UiMode
import io.github.livenlearnaday.mytictactoe.data.model.Cell
import io.github.livenlearnaday.mytictactoe.ui.component.CellWidget
import io.github.livenlearnaday.mytictactoe.ui.component.CommonAlertDialog
import io.github.livenlearnaday.mytictactoe.ui.component.CommonButton


@Composable
fun GameScreen(
    gameState: GameState,
    gameViewModel: GameViewModel,
    onHistoryButtonClicked: () -> Unit,
    onGameAction: (GameAction) -> Unit
) {

    val context = LocalContext.current
    val cells by gameViewModel.cells.collectAsState()

    if (gameState.isShowDialog) {
        CommonAlertDialog(
            onClose = {
                onGameAction(GameAction.DialogClose)
            },
            onConfirm = {
                when (gameState.uiMode) {
                    UiMode.RESET_ALL -> {
                        onGameAction(GameAction.StopRecording)
                        onGameAction(GameAction.ResetAll)
                    }

                    UiMode.RESET_GAME -> {
                        onGameAction(GameAction.StopRecording)
                        onGameAction(GameAction.ResetGame)
                    }

                    UiMode.NO_ACTION -> {
                    }
                }
            },
            onDismiss = {
                onGameAction(GameAction.DialogDismiss(gameState.uiMode))
            },
            dialogText = stringResource(R.string.dialog_reset_game_warning)
        )
    }

    LaunchedEffect(gameState.isMoved) {
        onGameAction(GameAction.OnPlayerMove)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    PlainTooltip {
                        Text(stringResource(R.string.reset))
                    }
                },
                state = rememberTooltipState(),
            ) {


                IconButton(
                    onClick = {
                        onGameAction(GameAction.OnClickResetIcon)
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp),
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null
                    )
                }
            }

            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    PlainTooltip {
                        Text(stringResource(R.string.history))
                    }
                },
                state = rememberTooltipState(),
            ) {
                IconButton(
                    onClick = {
                        onGameAction(GameAction.OnClickHistoryPlaysIcon)
                        onHistoryButtonClicked()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp),
                        imageVector = Icons.AutoMirrored.Outlined.List,
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = when {
                    gameState.winner.isWon -> stringResource(R.string.game_over)
                    else -> "${stringResource(R.string.player)} ${gameState.currentPlayer.name}'s ${
                        stringResource(
                            R.string.turn
                        )
                    }"
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {

            OutlinedButton(
                onClick = {
                    onGameAction(GameAction.OnClickMultiPlayerMode)
                },
                shape = CutCornerShape(
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (gameState.isSinglePlayer) Color.Transparent else MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            ) {
                Text(
                    stringResource(R.string.multiplayer),
                    color = if (gameState.isSinglePlayer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedButton(
                onClick = {
                    onGameAction(GameAction.OnClickSinglePlayerMode)
                },
                shape = CutCornerShape(
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (gameState.isSinglePlayer) MaterialTheme.colorScheme.primary else Color.Transparent
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            ) {
                Text(
                    stringResource(R.string.single_player),
                    color = if (gameState.isSinglePlayer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        for (i in 0 until gameState.gridSize) {
            Row {
                for (j in 0 until gameState.gridSize) {
                    val cell = Cell(
                        row = i,
                        column = j,
                        player = cells[i][j].player
                    )
                    CellWidget(
                        cell = cell,
                        onClick = {
                            onGameAction(GameAction.OnClickCell(cell, context))
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .padding(4.dp)
                    )
                }
            }
        }



        if (gameState.winner.isWon) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (gameState.winner.currentPlayer == Player.TIE) {
                    Text(
                        text = stringResource(R.string.no_winner_text),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(16.dp),
                    )
                } else {
                    Text(
                        text = "${stringResource(R.string.player)} ${gameState.winner.currentPlayer} ${
                            stringResource(
                                R.string.wins
                            )
                        }!",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(16.dp),
                    )
                }

                CommonButton(
                    onClick = {
                        onGameAction(GameAction.ResetGame)
                    },
                    displayText = stringResource(R.string.play_again),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyTicTacToeTheme {
        GameScreen(
            gameViewModel = GameViewModel(saveGameRecordUseCase = { }),
            onHistoryButtonClicked = { },
            gameState = GameState(),
            onGameAction = {}
        )
    }
}


