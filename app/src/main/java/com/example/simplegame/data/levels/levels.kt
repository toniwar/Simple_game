package com.example.simplegame.data.levels

import com.example.simplegame.domain.models.Level


class Level1(): Level {

    override val levelConfig = listOf<Int>(
        0,0,0,2,0,
        3,0,0,1,0,
        0,0,0,0,0,
        0,1,0,1,0,
        0,0,0,0,0)

}

class Level2(): Level{

    override val levelConfig = listOf<Int>(
        2,0,3,0,0,
        0,0,1,0,0,
        0,0,0,0,0,
        0,1,0,1,0,
        0,0,0,0,0

    )

}

class Level3(): Level{

    override val levelConfig = listOf<Int>(
        0,0,3,0,2,
        0,0,0,0,0,
        0,0,0,1,0,
        0,1,0,0,0,
        0,0,0,0,0

    )

}