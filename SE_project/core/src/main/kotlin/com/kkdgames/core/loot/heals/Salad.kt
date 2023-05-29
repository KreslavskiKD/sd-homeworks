package com.kkdgames.core.loot.heals

import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.Loot
import com.kkdgames.core.models.Player

class Salad(
    private val texture: Texture,
    private val owner: Player,
    x: Float,
    y: Float,
) : Loot() {
    init {
        this.x = x
        this.y = y
    }

    override fun equip(): Boolean {
        owner.heal(15)
        used = true
        return false
    }

    override fun unequip() {

    }

    override fun getTexture(): Texture {
        return texture
    }
}