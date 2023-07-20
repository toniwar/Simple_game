package com.example.simplegame.domain.use_cases

import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.Player
import kotlin.math.abs


class MoveEnemyUseCase {

    fun move(
        enemy: Enemy,
        player: Player,

    ): Int {

        var newPos = enemy.position
        val possibleMovements = possibleMovements(enemy.position)
        for(i in possibleMovements){
            val newValue = if(enemy.position + i in 1..25) enemy.position+i else enemy.position
            if(abs(player.position - newValue) < abs(player.position - newPos)) newPos = newValue
        }
        return newPos
    }

    private fun possibleMovements(enemyPos: Int): IntArray{
        val leftArray = intArrayOf(1, -4, 5, -5, 6)
        val rightArray = intArrayOf(-1, 4, 5, -5, -6)
        val array = intArrayOf(1, -1, 4, -4, 5, -5, 6, -6)
        if(enemyPos == 1) return leftArray
        if(enemyPos == 5) return rightArray
        var value = enemyPos

        while (true){
            value -= 5
            when{
                value == 1 -> return leftArray
                value == 0 -> return rightArray
                value < 0 -> return array
            }
        }

    }


}