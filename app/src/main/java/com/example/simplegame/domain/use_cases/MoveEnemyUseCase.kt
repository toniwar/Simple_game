package com.example.simplegame.domain.use_cases

import android.widget.GridLayout
import androidx.core.view.get
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.Player
import com.example.simplegame.presentation.views.WallView
import kotlin.random.Random.Default.nextInt


class MoveEnemyUseCase {

    fun move(
        enemy: Enemy,
        player: Player,
        gridLayout: GridLayout,
        previousPos: Int
        ): Int {

        var value = randomizer(enemy, player)
        if(gridLayout[value-1] !is WallView ) return value
        else while (true){
            value = alterMovement(enemy)
            if(gridLayout[value-1] !is WallView && value != previousPos) return value
        }


    }


    private fun randomizer(enemy: Enemy, player: Player): Int{
        var newPos = enemy.position
        val eL = enemy.location!!
        val pL = player.location!!
        when (nextInt(1, 3)) {
            1 -> {
                val value = enemyStep(eL.x, pL.x, 1)
                newPos += if (value == 404) enemyStep(eL.y, pL.y, 5)
                else value

            }

            2 -> {
                val value = enemyStep(eL.y, pL.y, 5)
                newPos += if (value == 404) enemyStep(eL.x, pL.x, 1)
                else value

            }
        }
        return newPos
    }


    private fun enemyStep(eL: Int, pL: Int, step: Int): Int {
        return if (eL - pL > 0) step * (-1)
        else if (eL - pL < 0 ) step
        else 404

    }

    private fun alterMovement(enemy: Enemy): Int{
        val possibleMovements = possibleMovements(enemy.position)
        while (true){
            val randomStep = nextInt(possibleMovements.size)
            val value = enemy.position + possibleMovements[randomStep]
            if( value in 1..25) return value
        }



    }

    private fun possibleMovements(enemyPos: Int): IntArray {
        val leftArray = intArrayOf(1, 5, -5 )
        val rightArray = intArrayOf(-1, 5, -5)
        val array = intArrayOf(1, -1, 5, -5)
        if (enemyPos == 1) return leftArray
        if (enemyPos == 5) return rightArray
        var value = enemyPos

        while (true) {
            value -= 5
            when {
                value == 1 -> return leftArray
                value == 0 -> return rightArray
                value < 0 -> return array
            }
        }
    }


}