package com.kkdgames.core.mobs

import com.badlogic.gdx.scenes.scene2d.Actor

interface MobFactory {
    fun giveMob(): Actor
}