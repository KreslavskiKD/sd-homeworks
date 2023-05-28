package com.kkdgames.core.mobs.rat

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.MathUtils
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.models.Player
import util.FontSizeHandler
import kotlin.math.abs
import kotlin.math.sign

class Rat (private val texture: Texture,
           private val sound: Sound,
           private val target: Player,
           val heightT: Float,
           private val strategy: Strategies,
           private val spawnPointX: Float,
           private val spawnPointY: Float,
) : Mob() {
    private var posX: Float = spawnPointX
    private var posY: Float = spawnPointY
    private val scale: Float = heightT / texture.height
    private var currentStep: Float = 2f // may be changed later

    private var curTargetPosX: Float = 0.0f
    private var curTargetPosY: Float = 0.0f
    private val attackDistance: Float = 25f
    private var lastAttackTime: Long = 0
    private var attackPeriodMillis: Long = 3 * 1000
    private var attackPower: Int = 25

    private val maxHealth = 100
    var health: Int = maxHealth

    private val halvedTextureWidth: Float = texture.width / 2f * scale
    private val halvedTextureHeight: Float = texture.height / 2f * scale
    private var font: BitmapFont = FontSizeHandler.INSTANCE.getFont((texture.width / 2 * scale).toInt(), Color.DARK_GRAY)

    private var lastChangeDirectionPassive: Long = 0
    private var walkPeriodMillis: Long = 2 * 1000
    private var curDir = 0

    init {
        x = spawnPointX - texture.width / 2 * scale
        y = spawnPointY - texture.height / 2 * scale
    }

    override fun act(delta: Float) {
        when (strategy) {
            Strategies.PASSIVE -> {
                passiveStrategy()
            }
            Strategies.ATTACKING -> {
                attackingStrategy()
            }
            Strategies.GETTING_AWAY -> {
                gettingAwayStrategy()
            }
        }
    }

    override fun passiveStrategy() {
        val curTimeMillis = System.currentTimeMillis()
        if (curTimeMillis - lastChangeDirectionPassive > walkPeriodMillis) {
            lastChangeDirectionPassive = curTimeMillis
            curDir = MathUtils.random.nextInt(5)
        }
        if (health > maxHealth / 5) {
            when (curDir) {
                0 -> {
                    x += currentStep
                }
                1 -> {
                    x -= currentStep
                }
                2 -> {
                    y += currentStep
                }
                3 -> {
                    y -= currentStep
                }
                4 -> {
                    // do nothing
                }
            }
        } else {
            x += currentStep / 2
        }
    }

    fun getCenterX(): Float {
        return x + halvedTextureWidth
    }

    fun getCenterY(): Float {
        return y + halvedTextureHeight
    }

    override fun setTargetPos(x: Float, y: Float) {
        curTargetPosX = x
        curTargetPosY = y
    }

    override fun receiveDamage(damage: Int) {
        health = if (health - damage > 0) {
            health - damage
        } else {
            0
        }
    }

    override fun receiveHealing(heal: Int) {
        health = if (health + heal < 100) {
            health + heal
        } else {
            100
        }
    }


    override fun attackingStrategy() {
        currentStep = 4F
        val distToTargetX = getCenterX() - curTargetPosX
        val distToTargetY = getCenterY() - curTargetPosY
        val absDistToTargetY = abs(distToTargetY)
        val absDistToTargetX = abs(distToTargetX)

        if (absDistToTargetY > absDistToTargetX) {
            if (absDistToTargetY > attackDistance) {
                y -= sign(distToTargetY) * currentStep
            }
        } else {
            if (absDistToTargetX > attackDistance) {
                x -= sign(distToTargetX) * currentStep
            }
        }

        val curTimeMillis = System.currentTimeMillis()
        if ((absDistToTargetX <= attackDistance && absDistToTargetY <= attackDistance)
            && curTimeMillis - lastAttackTime > attackPeriodMillis
            && target.health > 0
        ) {
            lastAttackTime = curTimeMillis
            sound.play()
            target.decreaseHealth(attackPower)
        }
    }

    private fun gettingAwayStrategy() {
        // TODO
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        font.draw(batch, health.toString(), x, y)
        batch.draw(texture, x, y, texture.width * scale, texture.height * scale)
    }

    companion object {
        enum class Strategies {
            PASSIVE,
            ATTACKING,
            GETTING_AWAY,
        }
    }
}