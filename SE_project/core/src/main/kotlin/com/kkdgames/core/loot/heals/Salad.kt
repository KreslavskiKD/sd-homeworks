package com.kkdgames.core.loot.heals

import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.Loot

class Salad(
    private val texture: Texture,
    x: Float,
    y: Float,
) : Loot() {
    init {
        this.x = x
        this.y = y
    }

    override fun equip(): Boolean {
        TODO("Not yet implemented")
    }

    override fun unequip() {
        TODO("Not yet implemented")
    }

    override fun getTexture(): Texture {
        return texture
    }
}