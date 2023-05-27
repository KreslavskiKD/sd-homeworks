package com.kkdgames.core.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

class Assets {
    var manager: AssetManager = AssetManager()

    fun load() {
        manager.load(playerFirstStageTexture)
        manager.load(playerTexture)
    }

    fun dispose() {
        manager.dispose()
    }

    companion object {
        val playerFirstStageTexture = AssetDescriptor(
            "textures/pickle-first-stage.png",
            Texture::class.java
        )

        val playerTexture = AssetDescriptor(
            "textures/pickle-pixel.png",
            Texture::class.java
        )
    }
}