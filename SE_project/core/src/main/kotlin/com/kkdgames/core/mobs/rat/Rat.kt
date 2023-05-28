package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor

class Rat (private val texture: Texture,
           val heightT: Float,
           private val strategy: Strategies,
           private val spawnPointX: Float,
           private val spawnPointY: Float,
) : Actor() {
    private var posX: Float = spawnPointX
    private var posY: Float = spawnPointY
    private val scale: Float = heightT / texture.height
    private var currentStep: Float = 2f // may be changed later

    var health: Int = 10

    override fun act(delta: Float) {
        when (strategy) {
            Strategies.PASSIVE -> {
                passiveStrategy(delta)
            }
            Strategies.ATTACKING -> {
                // todo
            }
            Strategies.GETTING_AWAY -> {
                // todo
            }
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(
            texture, posX, posY,
            texture.width * scale,
            texture.height * scale
        )
    }

    private fun passiveStrategy(f: Float) {
        if (health > 2) {
            when (MathUtils.random.nextInt(5)) {
                0 -> {
                    posX += currentStep
                }
                1 -> {
                    posX -= currentStep
                }
                2 -> {
                    posY += currentStep
                }
                3 -> {
                    posY -= currentStep
                }
                4 -> {
                    // do nothing
                }
            }
        } else {
            posX += currentStep
        }
    }

    companion object {
        enum class Strategies {
            PASSIVE,
            ATTACKING,
            GETTING_AWAY,
        }
    }
}