package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kkdgames.core.mobs.MobFactory
import com.kkdgames.core.util.Assets

class RatFactory (
    private val assets: Assets,
    private val viewportHeight: Float,
    private val viewportWidth: Float,
): MobFactory {
    override fun giveMob(): Actor {
        return Rat(
            texture = assets.manager.get(Assets.ratTexture),
            heightT = viewportHeight / 3.5f,    // maybe should be changed later
            strategy = Rat.Companion.Strategies.AGRESSIVE,
            spawnPointX = MathUtils.random.nextFloat() * viewportHeight,
            spawnPointY = MathUtils.random.nextFloat() * viewportWidth,
        )
    }
}