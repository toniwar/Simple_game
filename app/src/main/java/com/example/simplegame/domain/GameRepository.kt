package com.example.simplegame.domain

import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.Player

interface GameRepository {
    fun downloadGameField(): List<GameCell>

    fun createNewPlayer(): Player

    fun createNewEnemy(): Enemy

    fun downloadPlayer(): Player



}