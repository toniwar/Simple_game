package com.example.simplegame.domain.use_cases

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.GameCell

class DownloadGameFieldUC(private val gameRepository: GameRepository) {
    fun downloadField() = gameRepository.downloadGameField()
}