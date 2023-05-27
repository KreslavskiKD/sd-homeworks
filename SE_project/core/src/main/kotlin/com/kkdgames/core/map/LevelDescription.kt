package com.kkdgames.core.map

import com.kkdgames.core.loot.LootFactory
import com.kkdgames.core.mobs.MobFactory

data class LevelDescription(
    val levelNum: Int,
    val mobsProbability: List<Pair<MobFactory, Float>>,
    val lootProbability: List<Pair<LootFactory, Float>>,
    val maxBosses: Int,
)