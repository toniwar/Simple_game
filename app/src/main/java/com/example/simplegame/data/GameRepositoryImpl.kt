package com.example.simplegame.data


import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.EmptyCell
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.ExitCell
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.Level
import com.example.simplegame.domain.models.Player
import com.example.simplegame.domain.models.Wall



class GameRepositoryImpl: GameRepository {

    private var enemyPos = 5
    override fun downloadGameField(level: Level): List<GameCell> {




        val field = mutableListOf<GameCell>()
        level.levelConfig.forEachIndexed { index, item ->
            when (item) {
                0 -> field.add(EmptyCell)
                1 -> field.add(Wall)
                2 -> {
                    enemyPos = index+1
                    field.add(EmptyCell)
                }
                3 -> field.add(ExitCell)
            }

        }
        return field.toList()
    }

    override fun createNewPlayer(): Player {
        return Player()
    }

    override fun createNewEnemy(): Enemy {
       return Enemy(Enemy.SIREN_HEAD, position = enemyPos)
    }

    override fun downloadPlayer(): Player {
        TODO("Not yet implemented")
    }


}