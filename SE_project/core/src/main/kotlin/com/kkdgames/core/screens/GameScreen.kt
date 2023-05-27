package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kkdgames.core.MainGame
import com.kkdgames.core.util.Assets
import com.kkdgames.core.models.Player

class GameScreen(val game: MainGame, val assets: Assets) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()
    private var touchPos: Vector3
    private var stage: Stage

    private val viewportWidth = 800f
    private val viewportHeight = 480f

    init {
        camera.setToOrtho(false, viewportWidth, viewportHeight)

        stage = Stage(
            ScalingViewport(
                Scaling.stretch,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat(),
                camera
            ), game.batch)

        setupStage()

        touchPos = Vector3()
    }

    private fun setupStage() {
        stage.addActor(Player(assets.manager.get(Assets.playerFirstStageTexture), viewportHeight / 3.5f))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0.2f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        camera.update()
        game.batch.projectionMatrix = camera.combined

        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) { }
    override fun hide() { }
    override fun pause() { }
    override fun resume() { }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun dispose() {
        stage.dispose()
    }
}