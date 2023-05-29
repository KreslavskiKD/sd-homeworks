package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.Factories
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.models.Player

class Rat (
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
    attackPower = 25,           // maybe also should be settable from factory?
    currentStep = 3F,
    droppableLoot = listOf(
        Pair(Factories.drillFactory, 0.4F),
        Pair(Factories.saladFactory, 0.05F),
    ),
)