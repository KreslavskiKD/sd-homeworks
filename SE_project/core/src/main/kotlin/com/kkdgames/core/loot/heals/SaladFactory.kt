package com.kkdgames.core.loot.heals

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class SaladFactory(
    private val assets: Assets,
    private val player: Player,
): LootFactory {
    override fun giveLoot(x: Float, y: Float): Loot {
        return Salad(
            assets.manager.get(Assets.saladTexture),
            player,
            x,
            y,
        )
    }
}