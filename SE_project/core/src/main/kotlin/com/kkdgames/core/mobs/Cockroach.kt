package com.kkdgames.core.mobs

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor

class Cockroach(private val texture: Texture,
                val heightT: Float,
                private val strategy: (Float) -> Unit,
                private val spawnPointX: Float,
                private val spawnPointY: Float,
) : Actor() {
    private var posX: Float = spawnPointX
    private var posY: Float = spawnPointY
    private val scale: Float = heightT / texture.height
    private val currentStep: Float = 2f // may be changed later

    var health: Int = 10

    override fun act(delta: Float) {
        strategy.invoke(delta)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(
            texture, posX, posY,
            texture.width * scale,
            texture.height * scale
        )
    }
}