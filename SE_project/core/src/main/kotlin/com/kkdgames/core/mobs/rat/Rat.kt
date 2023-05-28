package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
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
) : Mob(texture, sound, target, height0, strategy, spawnPointX, spawnPointY)