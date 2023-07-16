package com.example.simplegame.domain

import com.example.simplegame.domain.models.GameCell

interface GameRepository {
    fun downloadGameField(): List<GameCell>
    fun makeAMove(startCell: Int, finishCell: Int): GameCell
}