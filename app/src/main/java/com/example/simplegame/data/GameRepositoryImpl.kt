package com.example.simplegame.data

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.EmptyCell
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.ExitCell
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.Player
import kotlin.random.Random
import kotlin.random.nextInt


class GameRepositoryImpl: GameRepository {
    override fun downloadGameField(): List<GameCell> {
        val exitCellNumb = Random.nextInt(1..10)
        val field = mutableListOf<GameCell>()
        for (i in  0..24){
            if(i == exitCellNumb)field.add(ExitCell)
            else field.add(EmptyCell)

        }
        return field.toList()
    }

    override fun createNewPlayer(): Player {
        return Player()
    }

    override fun createNewEnemy(): Enemy {
       return Enemy(Enemy.SIREN_HEAD, position = Random.nextInt(1..10))
    }

    override fun downloadPlayer(): Player {
        TODO("Not yet implemented")
    }


}