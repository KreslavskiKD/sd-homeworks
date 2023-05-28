package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kkdgames.core.mobs.MobFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class RatFactory (
    private val assets: Assets,
    private val viewportHeight: Float,
    private val viewportWidth: Float,
    private val player: Player,
    private val strategy: Rat.Companion.Strategies,
): MobFactory {
    override fun giveMob(): Actor {
        return Rat(
            texture = if (strategy != Rat.Companion.Strategies.ATTACKING) {
                assets.manager.get(Assets.ratTexture)
            } else {
                assets.manager.get(Assets.ratAggressiveTexture)
                   },
            sound = assets.manager.get(Assets.biteSound),
            target = player,
            heightT = viewportHeight / 3.5f,    // maybe should be changed later
            strategy = strategy,
            spawnPointX = MathUtils.random.nextFloat() * viewportHeight,
            spawnPointY = MathUtils.random.nextFloat() * viewportWidth,
        )
    }
}