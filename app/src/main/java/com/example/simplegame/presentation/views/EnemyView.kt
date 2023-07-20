package com.example.simplegame.presentation.views

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.simplegame.R

class EnemyView(
    private val context: Context
): LinearLayout(context) {


    init {
        initView()
    }

    private fun initView(){
        inflate(context, R.layout.game_unit_view, this)
        val avatar = findViewById<ImageView>(R.id.image_container)
        avatar.setImageResource(R.drawable.siren)
        orientation = HORIZONTAL

    }


}