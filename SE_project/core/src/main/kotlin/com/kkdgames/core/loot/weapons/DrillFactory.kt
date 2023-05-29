package com.kkdgames.core.loot.weapons

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory

class DrillFactory: LootFactory {
    override fun giveLoot(): Loot {
        return Drill()
    }
}