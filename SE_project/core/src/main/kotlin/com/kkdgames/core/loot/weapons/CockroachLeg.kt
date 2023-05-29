package com.kkdgames.core.loot.weapons

import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.Loot

class CockroachLeg(
    private val texture: Texture,
    x: Float,
    y: Float,
) : Loot() {
    init {
        this.x = x
        this.y = y
    }
    override fun equip() {
        TODO("Not yet implemented")
    }

    override fun unequip() {
        TODO("Not yet implemented")
    }

    override fun getTexture(): Texture {
        return texture
    }
}