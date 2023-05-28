package com.kkdgames.core.mobs.cockroach

import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kkdgames.core.mobs.MobFactory
import com.kkdgames.core.util.Assets

class CockroachFactory(
    private val assets: Assets,
    private val viewportHeight: Float,
    private val viewportWidth: Float,
): MobFactory {
    override fun giveMob(): Actor {
        return Cockroach(
            texture = assets.manager.get(Assets.cockroachTexture),
            heightT = viewportHeight / 3.5f,    // maybe should be changed later
            strategy = Cockroach.Companion.Strategies.PASSIVE,
            spawnPointX = random.nextFloat() * viewportHeight,
            spawnPointY = random.nextFloat() * viewportWidth,
        )
    }
}
