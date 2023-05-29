package com.kkdgames.core.loot.weapons

import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.Loot
import com.kkdgames.core.models.Player

class Drill(
    private val texture: Texture,
    private val owner: Player,
    x: Float,
    y: Float,
) : Loot() {
    init {
        this.x = x
        this.y = y
    }
    override fun equip(): Boolean {     // true if it stays in inventory, false if it gets removed (1 time use)
        owner.setAttackDistance(75f)
        owner.setAttackPower(40)
        used = true
        return true
    }

    override fun unequip() {
        owner.setAttackDistance(25f)
        owner.setAttackPower(10)
        used = false
    }

    override fun getTexture(): Texture {
        return texture
    }
}