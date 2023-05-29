package com.kkdgames.core.loot

interface LootFactory {
    fun giveLoot(x: Float, y: Float): Loot
}