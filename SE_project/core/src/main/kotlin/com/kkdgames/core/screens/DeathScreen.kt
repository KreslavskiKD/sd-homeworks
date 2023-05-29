package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kkdgames.core.MainGame
import com.kkdgames.core.util.Assets
import util.FontSizeHandler
import kotlin.system.exitProcess

class DeathScreen(private var game: MainGame, private val assets: Assets) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()

    private val viewportWidth = Gdx.graphics.width
    private val viewportHeight = Gdx.graphics.height

    private val backgroundTexture: Texture = assets.manager.get(Assets.cucumber)

    private val font: BitmapFont = FontSizeHandler.INSTANCE.getFont(Constants.FONT_SIZE, Color.RED)
    private val fontSmall: BitmapFont = FontSizeHandler.INSTANCE.getFont(Constants.FONT_SIZE, Color.WHITE)

    private var stage: Stage

    init {
        camera.setToOrtho(false, viewportWidth.toFloat(), viewportHeight.toFloat())

        stage = Stage(
            ScalingViewport(
                Scaling.stretch,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat(),
                camera
            ), game.batch
        )
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        game.batch.begin()

        game.batch.draw(
            backgroundTexture,
            viewportWidth / 2 - 500f,
            viewportHeight / 2 + 100f,
        )

        font.draw(
            game.batch,
            "DEAD, better luck next time",
            viewportWidth / 2 - 500f,
            viewportHeight / 2 - 0f,
        )

        fontSmall.draw(
            game.batch,
            "press space to restart",
            viewportWidth / 2 - 500f,
            viewportHeight / 2 - 200f,
        )

        game.batch.end()

        camera.update()
        game.batch.projectionMatrix = camera.combined

        stage.act()
        stage.draw()

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.screen = MainMenuScreen(game, assets)
            dispose()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose()
            exitProcess(0)
        }
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun resize(width: Int, height: Int) {}

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {}
}