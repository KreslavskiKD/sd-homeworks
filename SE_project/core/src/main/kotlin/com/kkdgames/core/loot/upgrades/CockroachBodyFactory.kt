package com.kkdgames.core.loot.upgrades

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory
import com.kkdgames.core.util.Assets

class CockroachBodyFactory(
    private val assets: Assets,
): LootFactory {
    override fun giveLoot(x: Float, y: Float): Loot {
        return CockroachBody(
            assets.manager.get(Assets.cockroachBody),
            x,
            y,
        )
    }
}