package com.kkdgames.core.mobs

import com.badlogic.gdx.scenes.scene2d.Actor

abstract class Mob : Actor() {
    abstract fun passiveStrategy()
    abstract fun attackingStrategy()
    abstract fun setTargetPos(x: Float, y: Float)
    abstract fun receiveDamage(damage: Int)
    abstract fun receiveHealing(heal: Int)
}