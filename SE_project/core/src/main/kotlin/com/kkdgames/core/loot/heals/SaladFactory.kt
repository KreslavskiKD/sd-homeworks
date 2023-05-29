package com.kkdgames.core.loot.heals

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory

class SaladFactory: LootFactory {
    override fun giveLoot(): Loot {
        return Salad()
    }
}