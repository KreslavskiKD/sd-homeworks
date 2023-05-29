package com.kkdgames.core.mobs.cockroach

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.upgrades.CockroachBodyFactory
import com.kkdgames.core.loot.weapons.CockroachLegFactory
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.models.Player

class Cockroach(
    texture: Texture,
    sound: Sound,
    target: Player,
    height0: Float,
    strategy: Companion.Strategies,
    spawnPointX: Float,
    spawnPointY: Float,
) : Mob(
    texture = texture,
    sound = sound,
    target = target,
    height0 = height0,
    strategy = strategy,
    spawnPointX = spawnPointX,
    spawnPointY = spawnPointY,
    attackPower = 15,           // maybe also should be settable from factory?
    currentStep = 4F,
    droppableLoot = listOf(
        Pair(CockroachLegFactory(), 0.4F),
        Pair(CockroachBodyFactory(), 0.05F),
    ),
)
