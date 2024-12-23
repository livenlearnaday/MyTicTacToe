package io.github.livenlearnaday.mytictactoe.data.model

import io.github.livenlearnaday.mytictactoe.data.enums.Player


data class Winner(
    var currentPlayer: Player = Player.EMPTY,
    var isWon: Boolean = false
)
