package com.example.simplegame.data

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.EmptyCell
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.Player


class GameRepositoryImpl: GameRepository {
    override fun downloadGameField(): List<GameCell> {
        val field = mutableListOf<GameCell>()
        for (i in  0..24){
            field.add(EmptyCell)
        }
        return field.toList()
    }

    override fun createNewPlayer(): Player {
        return Player()
    }

    override fun createNewEnemy(): Enemy {
        TODO("Not yet implemented")
    }

    override fun downloadPlayer(): Player {
        TODO("Not yet implemented")
    }


}