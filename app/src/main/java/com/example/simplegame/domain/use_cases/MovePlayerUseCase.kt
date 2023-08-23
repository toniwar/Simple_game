package com.example.simplegame.domain.use_cases

import android.view.View
import com.example.simplegame.domain.models.Player
import com.example.simplegame.presentation.views.WallView
import kotlin.math.abs

class MovePlayerUseCase {
    fun move(
        player: Player,
        currentX: Int,
        currentY: Int,
        clickX: Int,
        clickY: Int,
        currentID: Int,
        view: View

    ): Boolean {
        if(view is WallView) return false
        val clickID = view.id

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