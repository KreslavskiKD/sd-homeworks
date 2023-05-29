package com.kkdgames.core.loot

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle

abstract class Loot: Rectangle() {
    var taken: Boolean = false

    abstract fun equip()
    abstract fun unequip()
    fun getCenterX(): Float {
        return x + getTexture().width / 2
    }

    fun getCenterY(): Float {
        return y + getTexture().height / 2
    }
    abstract fun getTexture(): Texture
}