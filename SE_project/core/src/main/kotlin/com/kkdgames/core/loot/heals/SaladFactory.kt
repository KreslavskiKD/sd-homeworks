package com.kkdgames.core.loot.heals

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory
import com.kkdgames.core.util.Assets

class SaladFactory(
    private val assets: Assets,
): LootFactory {
    override fun giveLoot(x: Float, y: Float): Loot {
        return Salad(
            assets.manager.get(Assets.cucumber),
            x,
            y,
        )
    }
}