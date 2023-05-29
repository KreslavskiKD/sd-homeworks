package com.kkdgames.core.loot.weapons

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class CockroachLegFactory(
    private val assets: Assets,
    private val player: Player,
): LootFactory {
    override fun giveLoot(x: Float, y: Float): Loot {
        return CockroachLeg(
            assets.manager.get(Assets.cockroachLeg),
            player,
            x,
            y,
        )
    }
}