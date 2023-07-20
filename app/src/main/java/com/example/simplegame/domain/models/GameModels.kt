package com.example.simplegame.domain.models

import android.graphics.Point

sealed interface GameCell

object EmptyCell : GameCell

object ExitCell : GameCell

class NotEmptyCell(val gamePerc: GamePerc): GameCell


sealed interface GameUnit{
    var location: Point?
    var position:Int
}



class Enemy(
    val type: String,
    var step: Int = 1,
    override var position: Int,
    var isEnemyTurn: Boolean = false,
    override var location: Point? = null
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
