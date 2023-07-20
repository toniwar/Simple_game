package com.example.simplegame.domain.models

sealed interface GameCell

object EmptyCell : GameCell

class NotEmptyCell(val gamePerc: GamePerc): GameCell


sealed interface GameUnit

class Player(
    var step: Int = 1,
    var armor: Boolean = false,
    var alive: Boolean = true,
    var exit: Boolean = false,
    var score: Int = 0,
    var position: Int = PLAYER_START_POSITION,
    var isPlayerTurn: Boolean = true
): GameUnit{
    companion object{
        const val PLAYER_START_POSITION = 23
    }
}

class Enemy(
    val type: String,
    var step: Int = 1,
    var position: Int,
    var isEnemyTurn: Boolean = false
): GameUnit{
    companion object{
        const val SIREN_HEAD = "siren_head"
        const val PIPE_HEAD = "pipe_head"
    }
}

class GamePerc(val perc: String? = null){
    companion object{
        const val SHIELD = "shield"
        const val INCREASE_NEXT_STEP_BY_2 = "2X"
    }
}
