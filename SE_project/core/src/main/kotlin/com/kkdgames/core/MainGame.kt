package com.kkdgames.core

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.kkdgames.core.screens.MainMenuScreen
import com.kkdgames.core.util.Assets

class MainGame : Game() {
    lateinit var batch: SpriteBatch

    override fun create() {
        batch = SpriteBatch()
        val assets = Assets()
        assets.load()
        assets.manager.finishLoading()
        setScreen(MainMenuScreen(this, assets))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        batch.dispose()
    }
}