package com.kkdgames.core.loot.weapons

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class DrillFactory(
    private val assets: Assets,
    private val player: Player,
): LootFactory {
    override fun giveLoot(x: Float, y: Float): Loot {
        return Drill(
            assets.manager.get(Assets.drillTexture),
            player,
            x,
            y,
        )
    }
}