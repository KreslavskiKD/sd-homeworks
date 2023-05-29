package com.kkdgames.core.loot.upgrades

import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.LootFactory

class CockroachBodyFactory: LootFactory {
    override fun giveLoot(): Loot {
        return CockroachBody()
    }
}