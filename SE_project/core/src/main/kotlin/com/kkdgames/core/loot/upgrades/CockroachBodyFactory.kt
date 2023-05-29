package com.kkdgames.core.loot.upgrades

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class CockroachBodyFactory(
    private val assets: Assets,
    private val player: Player,
): LootFactory {
    override fun giveLoot(x: Float, y: Float): Loot {
        return CockroachBody(
            assets.manager.get(Assets.cockroachBody),
            player,
            x,
            y,
        )
    }
}