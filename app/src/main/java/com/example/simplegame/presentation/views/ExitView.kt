package com.example.simplegame.presentation.views

import android.content.Context
import android.widget.LinearLayout
import com.example.simplegame.R

class ExitView(
    private val context: Context,

    ): LinearLayout(context) {


    init {
        initView()
    }

    private fun initView(){
        inflate(context, R.layout.exit_cell, this)
        orientation = HORIZONTAL

    }

}