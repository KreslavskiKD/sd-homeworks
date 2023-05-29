package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx

enum class DIFFICULTY {
    HARD,
    EASY
}

object Constants {
    const val MAX_WIDTH = 1920f
    const val MAX_HEIGHT = 1080f

    const val MIN_WIDTH = 800f
    const val MIN_HEIGHT = 480f

    var FONT_SIZE = Gdx.graphics.width / 30
    var FONT_SIZE_SMALL = Gdx.graphics.width / 70

    val LEVEL = DIFFICULTY.HARD
}