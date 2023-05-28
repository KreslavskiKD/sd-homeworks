package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kkdgames.core.MainGame
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.mobs.cockroach.CockroachFactory
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class GameScreen(private val game: MainGame, private val assets: Assets) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()
    private var touchPos: Vector3
    private var stage: Stage

    private val viewportWidth = 800f
    private val viewportHeight = 480f

    private lateinit var player: Player
    private var mobGroup: Group

    init {
        camera.setToOrtho(false, viewportWidth, viewportHeight)

        stage = Stage(
            ScalingViewport(
                Scaling.stretch,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat(),
                camera
            ), game.batch
        )

        mobGroup = Group()

        setupStage()

        touchPos = Vector3()
    }

    private fun setupStage() {
        player = Player(
            assets.manager.get(Assets.playerFirstStageTexture),
            viewportHeight / 3.5f,
            viewportWidth / 2,
            viewportHeight / 2,
        )

        val cockroachFactory = CockroachFactory(assets, player, viewportHeight, viewportWidth)
        mobGroup.addActor(cockroachFactory.giveMob())

        stage.addActor(player)
        stage.addActor(mobGroup)
    }

    override fun render(delta: Float) {
        val backgroundTexture = assets.manager.get(Assets.background1)
        val backgroundSprite = Sprite(backgroundTexture)
        Gdx.gl.glClearColor(0F, 0F, 0.0f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        game.batch.begin()
        backgroundSprite.draw(game.batch, 1F)
        game.batch.end()

        camera.update()
        game.batch.projectionMatrix = camera.combined

        stage.act()
        updateMobsTarget()
        stage.draw()
    }

    private fun updateMobsTarget() {
        for (mob: Actor in mobGroup.children) { // maybe we want to add several mobs
            if (mob is Mob) {
                mob.setTargetPos(player.centerX, player.centerY)
            }
        }
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