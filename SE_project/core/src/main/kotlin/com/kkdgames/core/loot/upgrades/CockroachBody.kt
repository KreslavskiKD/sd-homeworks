package com.kkdgames.core.loot.upgrades

import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.Loot
import com.kkdgames.core.models.Player

class CockroachBody(
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
        owner.setAttackDistance(100f)
        owner.setAttackPower(20)
        owner.setCurrentStep(Player.STEP_UP_1)
        owner.setTexture(texture)
        used = true
        return true
    }

    override fun unequip() {
        owner.setAttackDistance(25f)
        owner.setAttackPower(10)
        owner.setDefaultTexture()
        used = false
    }

    override fun getTexture(): Texture {
        return texture
    }
}