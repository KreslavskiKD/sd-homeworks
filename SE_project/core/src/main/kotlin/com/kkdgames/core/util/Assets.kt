package com.kkdgames.core.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture


class Assets {
    var manager: AssetManager = AssetManager()

    fun load() {
        manager.load(bucketTexture)
        manager.load(dropletTexture)
        manager.load(dropletSound)
        manager.load(rainSound)
    }

    fun dispose() {
        manager.dispose()
    }

    companion object {
        val bucketTexture = AssetDescriptor(
            "textures/bucket.png",
            Texture::class.java
        )
        val dropletTexture = AssetDescriptor(
            "textures/drop.png",
            Texture::class.java
        )
        val dropletSound = AssetDescriptor(
            "sounds/drop.wav",
            Sound::class.java
        )
        val rainSound = AssetDescriptor(
            "sounds/rain.mp3",
            Music::class.java
        )
    }
}