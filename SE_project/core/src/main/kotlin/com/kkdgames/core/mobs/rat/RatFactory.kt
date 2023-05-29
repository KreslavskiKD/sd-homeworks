package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.math.MathUtils
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.mobs.MobFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class RatFactory(
    private val assets: Assets,
    private val viewportHeight: Int,
    private val viewportWidth: Int,
    private val player: Player,
    private val strategy: Mob.Companion.Strategies,
): MobFactory {
    override fun giveMob(): Mob {
        return Rat(
            texture = if (strategy != Mob.Companion.Strategies.ATTACKING) {
                assets.manager.get(Assets.ratTexture)
            } else {
                assets.manager.get(Assets.ratAggressiveTexture)
                   },
            sound = assets.manager.get(Assets.biteSound),
            target = player,
            height0 = viewportHeight / 3.5f,    // maybe should be changed later
            strategy = strategy,
            spawnPointX = MathUtils.random.nextFloat() * viewportHeight,
            spawnPointY = MathUtils.random.nextFloat() * viewportWidth,
            assets = assets,
        )
    }
}