package com.kkdgames.core.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture


class Assets {
    companion object {
    val playerTexture = AssetDescriptor(
        "textures/pickle-pixel.png",
        Texture::class.java
    )
}
    var manager: AssetManager = AssetManager()

    fun load() {
        manager.load(playerTexture)
    }

    fun dispose() {
        manager.dispose()
    }
}