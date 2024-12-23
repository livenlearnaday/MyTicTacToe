package io.github.livenlearnaday.mytictactoe.data.model

import io.github.livenlearnaday.mytictactoe.data.enums.Player

data class Cell(
    val row: Int,
    val column: Int,
    val player: Player
)

