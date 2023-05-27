package com.kkdgames.core

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.kkdgames.core.screens.MainMenuScreen
import com.kkdgames.core.util.Assets

class MainGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont() // use libGDX's default Arial font
        val assets = Assets()
        assets.load() //starts loading assets

        assets.manager.finishLoading()
        setScreen(MainMenuScreen(this, assets))
    }

    override fun render() {
        super.render() // important!
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}