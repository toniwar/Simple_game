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
    var position: Int = 23
): GameUnit

class Enemy(
    val type: String,
    var step: Int = 1,
    var position: Int
): GameUnit

class GamePerc(val perc: String? = null){
    companion object{
        const val SHIELD = "shield"
        const val INCREASE_NEXT_STEP_BY_2 = "2X"
    }
}
