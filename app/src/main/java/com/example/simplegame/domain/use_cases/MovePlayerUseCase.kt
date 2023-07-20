package com.example.simplegame.domain.use_cases

import com.example.simplegame.domain.models.Player
import kotlin.math.abs

class MovePlayerUseCase {
    fun move(
        player: Player,
        currentX: Int,
        currentY: Int,
        clickX: Int,
        clickY: Int,
        currentID: Int,
        clickID: Int

    ): Boolean {
        if (player.isPlayerTurn) {
            if (currentY == clickY && abs(currentID - clickID) == 1) return true
            else if (currentY > clickY){
                if(currentX == clickX && abs(currentID - clickID) == 5) return true
                else if(currentX > clickX && abs(currentID - clickID) == 6) return true
                else if(currentX < clickX && abs(currentID - clickID) == 4) return true
            }
            else if (currentY < clickY){
                if(currentX == clickX && abs(currentID - clickID) == 5) return true
                else if(currentX > clickX && abs(currentID - clickID) == 4) return true
                else if(currentX < clickX && abs(currentID - clickID) == 6) return true
            }

        }
        return false
    }

}