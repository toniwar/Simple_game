package com.example.simplegame.presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplegame.R
import com.example.simplegame.domain.models.GameCell

class GameFieldAdapter: RecyclerView.Adapter<GameFieldAdapterViewHolder>() {

    private val gameFieldCells = mutableListOf<GameCell>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFieldAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.grid_cell, parent)
        return GameFieldAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gameFieldCells.size
    }

    override fun onBindViewHolder(holder: GameFieldAdapterViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun initGameField(field: List<GameCell>){
        field.map { gameFieldCells.add(it) }
    }
}