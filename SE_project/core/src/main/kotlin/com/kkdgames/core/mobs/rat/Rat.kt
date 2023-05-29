package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.kkdgames.core.loot.heals.SaladFactory
import com.kkdgames.core.loot.weapons.Drill
import com.kkdgames.core.loot.weapons.DrillFactory
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class Rat (
    texture: Texture,
    sound: Sound,
    target: Player,
    height0: Float,
    strategy: Companion.Strategies,
    spawnPointX: Float,
    spawnPointY: Float,
    assets: Assets,
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
        Pair(DrillFactory(assets), 0.4F),
        Pair(SaladFactory(assets), 0.05F),
    ),
)