package com.kkdgames.core.mobs.cockroach

import com.badlogic.gdx.math.MathUtils.random
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.mobs.MobFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class CockroachFactory(
    private val assets: Assets,
    private val player: Player,
    private val viewportHeight: Int,
    private val viewportWidth: Int,
    private val strategy: Mob.Companion.Strategies,
): MobFactory {
    override fun giveMob(): Mob {
        return Cockroach(
            texture = assets.manager.get(Assets.cockroachTexture),
            sound = assets.manager.get(Assets.biteSound),
            target = player,
            height0 = viewportHeight / 4f,
            strategy = strategy,
            spawnPointX = random.nextFloat() * viewportHeight,
            spawnPointY = random.nextFloat() * viewportWidth,
            assets = assets,
        )
    }
}
