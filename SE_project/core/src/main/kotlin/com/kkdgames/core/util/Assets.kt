package com.kkdgames.core.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

class Assets {
    var manager: AssetManager = AssetManager()

    fun load() {
        manager.load(playerFirstStageTexture)
        manager.load(playerTexture)
        manager.load(background1)
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

        val cockroachTexture = AssetDescriptor(
            "textures/cockroach.png",
            Texture::class.java
        )

        val ratTexture = AssetDescriptor(
            "textures/rat.png",
            Texture::class.java
        )

        val background1 = AssetDescriptor(
            "backgrounds/background1.png",
            Texture::class.java
        )
    }
}