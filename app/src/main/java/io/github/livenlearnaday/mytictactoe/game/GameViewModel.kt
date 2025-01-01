package io.github.livenlearnaday.mytictactoe.game

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.data.enums.UiMode
import io.github.livenlearnaday.mytictactoe.data.model.Cell
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.data.model.Winner
import io.github.livenlearnaday.mytictactoe.media.MyMediaRecorderManager
import io.github.livenlearnaday.mytictactoe.media.MyMediaRecorderManager.isMediaRecorderError
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.SaveGameRecordUseCase
import io.github.livenlearnaday.mytictactoe.utils.checkAntiDiagonalWin
import io.github.livenlearnaday.mytictactoe.utils.checkColumnWin
import io.github.livenlearnaday.mytictactoe.utils.checkDiagonalWin
import io.github.livenlearnaday.mytictactoe.utils.checkRowWin
import io.github.livenlearnaday.mytictactoe.utils.deleteFileByFileName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val saveGameRecordUseCase: SaveGameRecordUseCase
) : ViewModel() {

    companion object {
        const val DEFAULT_GRID_SIZE = 3
    }

    var gameState by mutableStateOf(GameState())
        private set

    private val _cells: MutableStateFlow<MutableList<MutableList<Cell>>> =
        MutableStateFlow(MutableList(gameState.gridSize) {
            MutableList(gameState.gridSize) {
                Cell(
                    row = 0,
                    column = 0,
                    player = Player.EMPTY
                )
            }
        })
    val cells = _cells.asStateFlow()

    fun gameAction(action: GameAction) {
        when (action) {
            is GameAction.ResetGame -> {
                resetGame()
                updateUiModeAndDialogTrigger(UiMode.NO_ACTION, false)
            }

            is GameAction.ResetAll -> {
                resetAll()
            }

            GameAction.DialogClose -> {
                updateIsShowDialog(false)
            }

            is GameAction.DialogDismiss -> {
                if (gameState.isChangedPlayerMode) updateIsSinglePlayer(
                    !gameState.isSinglePlayer,
                    false
                )
                updateUiModeAndDialogTrigger(UiMode.NO_ACTION, false)
            }

            GameAction.OnPlayerMove -> {
                if (gameState.isMoved && gameState.isSinglePlayer && !gameState.winner.isWon) {
                    makeAIMove()
                }
            }

            is GameAction.OnGridSizeChange -> {
                updateGameBoard(action.gridSizeString)
            }

            GameAction.OnClickResetIcon -> {
                when {
                    gameState.moveCount == 0 -> resetAll()
                    else -> {
                        updateUiModeAndDialogTrigger(UiMode.RESET_ALL, true)
                    }
                }
            }

            GameAction.OnClickHistoryPlaysIcon -> {
                MyMediaRecorderManager.releaseMediaRecorderResource()
                resetGame()
            }

            GameAction.OnClickMultiPlayerMode -> {
                when {
                    gameState.moveCount == 0 -> updateIsSinglePlayer(false, false)

                    else -> {
                        updateIsSinglePlayer(false, true)
                        updateUiModeAndDialogTrigger(UiMode.RESET_GAME, true)
                    }
                }
            }

            GameAction.OnClickSinglePlayerMode -> {
                when {
                    gameState.moveCount == 0 -> updateIsSinglePlayer(true, false)
                    else -> {
                        updateIsSinglePlayer(true, true)
                        updateUiModeAndDialogTrigger(UiMode.RESET_GAME, true)
                    }
                }
            }

            is GameAction.OnClickCell -> {
                when {
                    gameState.moveCount == 0 -> {
                        startScreenRecording(action.context)
                        updateFileName()
                        updateIsMoved(
                            makeMove(
                                action.cell.row,
                                action.cell.column
                            )
                        )

                    }

                    !gameState.winner.isWon -> updateIsMoved(
                        makeMove(
                            action.cell.row,
                            action.cell.column
                        )
                    )

                    gameState.winner.isWon && gameState.moveCount > 2 -> stopScreenRecording()

                    else -> { } // no op

                }
            }

            GameAction.StopRecording -> {
                stopScreenRecording()
            }
        }
    }

    private fun updateUiModeAndDialogTrigger(uiMode: UiMode, isShowDialog: Boolean) {
        updateUiMode(uiMode)
        updateIsShowDialog(isShowDialog)
    }


    private fun makeMove(row: Int, col: Int): Boolean {

        if (_cells.value[row][col].player == Player.EMPTY) {
            updateCell(Cell(row = row, column = col, player = gameState.currentPlayer))
            updateCurrentPlayer(if (gameState.currentPlayer == Player.X) Player.O else Player.X)

            checkGameWin()
            return true
        }

        return false
    }

    private fun makeAIMove() {
        val emptyCells = mutableListOf<Cell>()

        for (i in 0 until gameState.gridSize) {
            for (j in 0 until gameState.gridSize) {
                if (_cells.value[i][j].player == Player.EMPTY) {
                    emptyCells.add(Cell(row = i, column = j, player = Player.EMPTY))
                }
            }
        }

        if (emptyCells.isNotEmpty()) {
            val randomCell = emptyCells.random()
            if (_cells.value[randomCell.row][randomCell.column].player == Player.EMPTY) {

                updateCell(
                    Cell(
                        row = randomCell.row,
                        column = randomCell.column,
                        player = gameState.currentPlayer
                    )
                )



                updateCurrentPlayer(if (gameState.currentPlayer == Player.X) Player.O else Player.X)

                checkGameWin()
            }
        }

        // set ai move to false after ai is done
        updateIsMoved(false)
    }

    private fun updateIsMoved(isMoved: Boolean) {
        gameState = gameState.copy(
            isMoved = isMoved
        )
    }


    private fun checkGameWin() {
        if (gameState.moveCount > 2) {

            // Check rows
            for (row in 0 until gameState.gridSize) {
                val rowsWithPLayerX = checkRowWin(row, Player.X, _cells.value, gameState.gridSize)
                val rowsWithPLayerO = checkRowWin(row, Player.O, _cells.value, gameState.gridSize)

                when {
                    rowsWithPLayerX -> {
                        updateWinner(Player.X)
                        return
                    }

                    rowsWithPLayerO -> {
                        updateWinner(Player.O)
                        return
                    }

                    else -> {
                        // no op
                    }
                }

            }


            // Check columns
            for (col in 0 until gameState.gridSize) {
                val colWithPLayerX = checkColumnWin(col, Player.X, _cells.value, gameState.gridSize)
                val colWithPLayerO = checkColumnWin(col, Player.O, _cells.value, gameState.gridSize)

                when {
                    colWithPLayerX -> {
                        updateWinner(Player.X)
                        return
                    }

                    colWithPLayerO -> {
                        updateWinner(Player.O)
                        return
                    }

                    else -> {
                        // no op
                    }
                }
            }

            // Check diagonals
            val mainDiagWithPlayerX = checkDiagonalWin(Player.X, _cells.value, gameState.gridSize)
            val mainDiagWithPlayerO = checkDiagonalWin(Player.O, _cells.value, gameState.gridSize)

            when {
                mainDiagWithPlayerX -> {
                    updateWinner(Player.X)
                    return
                }

                mainDiagWithPlayerO -> {
                    updateWinner(Player.O)
                    return
                }

                else -> {
                    // no op
                }
            }

            val antiDiagWithPlayerX =
                checkAntiDiagonalWin(Player.X, _cells.value, gameState.gridSize)
            val antiDiagWithPlayerO =
                checkAntiDiagonalWin(Player.O, _cells.value, gameState.gridSize)

            when {
                antiDiagWithPlayerX -> {
                    updateWinner(Player.X)
                    return
                }

                antiDiagWithPlayerO -> {
                    updateWinner(Player.O)
                    return
                }

                else -> {
                    // no op
                }
            }

            // Check for a tie
            if (_cells.value.all { row ->
                    row.all {
                        it.player != Player.EMPTY
                    }
                }) {

                updateWinner(Player.TIE)

            }
        }
    }


    private fun resetGame() {
        if (!gameState.winner.isWon) gameState.currentGameFileName.deleteFileByFileName()
        gameState = gameState.copy(
            winner = Winner(currentPlayer = Player.EMPTY, isWon = false),
            currentPlayer = Player.X,
            isMoved = false,
            moveCount = 0,
            currentGameFileName = ""
        )
        resetCells(gameState.gridSize)

    }

    private fun updateCurrentPlayer(currentPlayer: Player) {
        gameState = gameState.copy(
            currentPlayer = currentPlayer
        )
    }

    private fun updateUiMode(uiMode: UiMode) {
        gameState = gameState.copy(
            uiMode = uiMode
        )
    }

    private fun updateIsShowDialog(isShowDialog: Boolean) {
        gameState = gameState.copy(
            isShowDialog = isShowDialog
        )
    }


    private fun updateIsSinglePlayer(isSinglePlayer: Boolean, isChangedPlayerMode: Boolean) {
        gameState = gameState.copy(
            isSinglePlayer = isSinglePlayer,
            isChangedPlayerMode = isChangedPlayerMode
        )
    }

    private fun resetAll() {
        resetGame()
        updateUiModeAndDialogTrigger(UiMode.NO_ACTION, false)
    }

    private fun updateGridSize(gridSize: Int) {
        gameState = gameState.copy(
            gridSize = gridSize
        )
    }

    private fun resetCells(gridSize: Int) {
        _cells.value = MutableList(gridSize) { row ->
            MutableList(gridSize) { col ->
                Cell(row = row, column = col, player = Player.EMPTY)
            }
        }
    }

    private fun updateCell(cell: Cell) {
        _cells.value[cell.row][cell.column] = cell

        incrementMoveCount()
    }

    private fun incrementMoveCount() {
        gameState = gameState.copy(
            moveCount = gameState.moveCount + 1
        )
    }

    private fun updateWinner(player: Player) {
        gameState = gameState.copy(
            winner = Winner(currentPlayer = player, isWon = true)
        )

        if (!isMediaRecorderError) {
            saveGameRecord(
                GameRecord(
                    fileName = gameState.currentGameFileName,
                    filePath = MyMediaRecorderManager.filePath,
                    winner = "Player ${gameState.winner.currentPlayer}"
                )
            )
        }
    }

    private fun updateGameBoard(gridSize: String) {
        val gridSizeToUpdate = gridSize.toIntOrNull() ?: DEFAULT_GRID_SIZE
        updateGridSize(gridSizeToUpdate)
        resetGame()
    }

    private fun saveGameRecord(gameRecord: GameRecord) {
        viewModelScope.launch {
            saveGameRecordUseCase.execute(gameRecord)
        }
    }

    private fun startScreenRecording(context: Context) {
        MyMediaRecorderManager.startScreenRecord(context)
    }

    private fun stopScreenRecording() {
        MyMediaRecorderManager.stopScreenRecord()
    }

    private fun updateFileName() {
        gameState = gameState.copy(
            currentGameFileName = MyMediaRecorderManager.fileName
        )
    }


}