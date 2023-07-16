package com.example.simplegame.data

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.EmptyCell
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.GamePerc
import com.example.simplegame.domain.models.NotEmptyCell
import com.example.simplegame.domain.models.Player
import kotlin.random.Random.Default.nextInt

class GameRepositoryImpl: GameRepository {
    override fun downloadGameField(): List<GameCell> {
        val field = mutableListOf<GameCell>()
        val enemyPosition = nextInt(9)
        val enemy = Enemy(Enemy.SIREN_HEAD, position = enemyPosition)
        val player = Player()
        for (i in  0..24){
            when(i){
                enemyPosition -> field.add(NotEmptyCell(enemy))
                player.position -> field.add(NotEmptyCell(player))
                else -> field.add(EmptyCell(GamePerc()))
            }
        }
        return field.toList()
    }

    override fun makeAMove(startCell: Int, finishCell: Int): GameCell {
        TODO("Not yet implemented")
    }
}