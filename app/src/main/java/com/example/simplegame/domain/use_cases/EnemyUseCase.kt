package com.example.simplegame.domain.use_cases

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.Enemy

class EnemyUseCase(private val gameRepository: GameRepository) {
    fun createNewEnemy(): Enemy{
        return gameRepository.createNewEnemy()
    }
}