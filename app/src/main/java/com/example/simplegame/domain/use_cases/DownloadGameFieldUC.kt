package com.example.simplegame.domain.use_cases

import com.example.simplegame.domain.GameRepository
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.Level

class DownloadGameFieldUC(private val gameRepository: GameRepository) {
    fun downloadField(level: Level) = gameRepository.downloadGameField(level)
}