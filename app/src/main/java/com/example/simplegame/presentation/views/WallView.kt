package com.example.simplegame.presentation.views

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.simplegame.R

class WallView (
    private val context: Context,

    ): LinearLayout(context) {


    init {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.grid_cell, this)
        findViewById<ImageView>(R.id.cell_image_container).setImageResource(R.drawable.wall)
        orientation = HORIZONTAL

    }
}