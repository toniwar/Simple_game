package com.example.simplegame.domain.models

sealed interface GameCell

class EmptyCell(gamePerc: GamePerc) : GameCell

class NotEmptyCell(unit: GameUnit): GameCell


sealed interface GameUnit

class Player(
    var step: Int = 1,
    var armor: Boolean = false,
    var alive: Boolean = true,
    var exit: Boolean = false,
    var score: Int = 0,
    var position: Int = PLAYER_START_POSITION
): GameUnit{
    companion object{
        const val PLAYER_START_POSITION = 22
    }
}

class Enemy(
    val type: String,
    var step: Int = 1,
    var position: Int
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
