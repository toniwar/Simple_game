package com.example.simplegame.domain.models


import android.graphics.Point
import com.example.simplegame.R


class Player(
    var step: Int = 1,
    var armor: Boolean = false,
    var alive: Boolean = true,
    var exit: Boolean = false,
    var score: Int = 0,
    override var position: Int = PLAYER_START_POSITION,
    override var location: Point? = null,
    var isPlayerTurn: Boolean = true,
    val avatar: Int = R.drawable.player
): GameUnit{
    companion object{
        const val PLAYER_START_POSITION = 23
    }
}