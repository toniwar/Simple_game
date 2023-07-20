package com.example.simplegame.domain.use_cases

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.Player

class PlayerUseCase(private val gameRepository: GameRepository) {

    fun createNewPlayer():Player{
        return gameRepository.createNewPlayer()
    }
}