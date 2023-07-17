package com.example.simplegame.presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplegame.R
import com.example.simplegame.domain.models.GameCell

class GameFieldAdapter: RecyclerView.Adapter<GameFieldAdapterViewHolder>() {

    var cellWidth: Int? = null

    private val gameFieldCells = mutableListOf<GameCell>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFieldAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.grid_cell, parent, false)
        return GameFieldAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gameFieldCells.size
    }

    override fun onBindViewHolder(holder: GameFieldAdapterViewHolder, position: Int) {
        holder.binds(cellWidth, gameFieldCells[position])


    }

    fun initGameField(field: List<GameCell>){
        field.forEach{ gameFieldCells.add(it) }
    }



}