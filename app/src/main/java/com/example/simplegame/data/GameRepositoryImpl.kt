package com.example.simplegame.data

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.EmptyCell
import com.example.simplegame.domain.models.GameCell
import kotlin.random.Random.Default.nextInt

class GameRepositoryImpl: GameRepository {
    override fun downloadGameField(): List<GameCell> {
        val field = mutableListOf<GameCell>()
        val enemyPosition = nextInt(9)
        for (i in  0..24){
            field.add(EmptyCell)
        }
        return field.toList()
    }

    override fun makeAMove(startCell: Int, finishCell: Int): GameCell {
        TODO("Not yet implemented")
    }
}