package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.kkdgames.core.MainGame
import com.kkdgames.core.util.Assets

class MainMenuScreen(private var game: MainGame, val assets: Assets):Screen {

    private var camera: OrthographicCamera = OrthographicCamera()

    init {
        camera.setToOrtho(false, 800f, 480f)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0.2f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin();
        game.font.draw(game.batch, "Welcome", 100f, 150f);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100f, 100f);
        game.batch.end();

        if (Gdx.input.isTouched) {
            game.screen = GameScreen(game, assets)
            dispose();
        }
    }

    override fun show() {}

    override fun resize(width: Int, height: Int) {}

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {}
}