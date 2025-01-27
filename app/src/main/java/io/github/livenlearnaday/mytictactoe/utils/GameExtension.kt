package io.github.livenlearnaday.mytictactoe.utils

import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.data.model.Cell

fun checkRowWin(row: Int, player: Player, cells: MutableList<MutableList<Cell>>, gridSize: Int): Boolean{

    val resultFor3By3Grid = (0 until gridSize).all { cells[row][it].player == player }

    return resultFor3By3Grid
}

fun checkColumnWin(column: Int, player: Player, cells: MutableList<MutableList<Cell>>, gridSize: Int): Boolean{

    val resultFor3By3Grid = (0 until gridSize).all { cells[it][column].player == player }

    return resultFor3By3Grid
}

fun checkDiagonalWin(player: Player, cells: MutableList<MutableList<Cell>>, gridSize: Int): Boolean {

    val resultFor3By3Grid = (0 until gridSize).all { cells[it][it].player == player }

    return  resultFor3By3Grid
}

fun checkAntiDiagonalWin(player: Player, cells: MutableList<MutableList<Cell>>, gridSize: Int): Boolean {

    val resultFor3By3Grid = (0 until gridSize).all { cells[it][(gridSize-1)-it].player == player }

    return resultFor3By3Grid
}