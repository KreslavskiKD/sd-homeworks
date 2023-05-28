package com.kkdgames.core.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture

class Assets {
    var manager: AssetManager = AssetManager()

    fun load() {
        manager.load(playerFirstStageTexture)
        manager.load(playerTexture)
        manager.load(background1)
        manager.load(cockroachTexture)
        manager.load(wayTexture)
        manager.load(ratTexture)
        manager.load(ratAggressiveTexture)
        manager.load(biteSound)
        manager.load(drillTexture)
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

        val ratAggressiveTexture = AssetDescriptor(
            "textures/rat-aggressive.png",
            Texture::class.java
        )

        val wayTexture = AssetDescriptor(
            "textures/way.png",
            Texture::class.java
        )

        val drillTexture = AssetDescriptor(
            "textures/drill.png",
            Texture::class.java
        )

        val background1 = AssetDescriptor(
            "backgrounds/background1.png",
            Texture::class.java
        )


        val  biteSound = AssetDescriptor(
            "sounds/mob_bite.wav",
            Sound::class.java
        )
    }
}