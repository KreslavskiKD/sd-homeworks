package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kkdgames.core.MainGame
import com.kkdgames.core.map.Direction
import com.kkdgames.core.map.LevelDescription
import com.kkdgames.core.mobs.cockroach.CockroachFactory
import com.kkdgames.core.mobs.rat.RatFactory
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.models.Player
import com.kkdgames.core.util.Assets

class GameScreen(private val game: MainGame, private val assets: Assets) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()
    private var touchPos: Vector3
    private var stage: Stage

    private val viewportWidth = 800f
    private val viewportHeight = 480f

    private val mapX = 5
    private val mapY = 5

    private var curX = 2
    private var curY = 2

    private var player: Player = Player(
        assets.manager.get(Assets.playerFirstStageTexture),
        viewportHeight / 3.5f,
        viewportWidth / 2,
        viewportHeight / 2,
    )

    private var mobGroup: Group

    private val cockroachFactory = CockroachFactory(
        assets = assets,
        viewportHeight = viewportHeight,
        viewportWidth = viewportWidth,
        player = player,
    )

    private val ratFactory = RatFactory(
        assets = assets,
        viewportHeight = viewportHeight,
        viewportWidth = viewportWidth,
    )

    private val levels = listOf(
        LevelDescription(
            levelNum = 1,
            mobsProbability = listOf(Pair(cockroachFactory, 0.4F), Pair(ratFactory, 0.1F)),
            lootProbability = listOf(),     // todo
            maxBosses = 1,
        ),
        LevelDescription(
            levelNum = 2,
            mobsProbability = listOf(Pair(cockroachFactory, 0.4F), Pair(ratFactory, 0.4F)),
            lootProbability = listOf(),     // todo
            maxBosses = 1,
        ),
        LevelDescription(
            levelNum = 3,
            mobsProbability = listOf(Pair(cockroachFactory, 0.6F), Pair(ratFactory, 0.4F)),
            lootProbability = listOf(),     // todo
            maxBosses = 4,
        ),
        LevelDescription(
            levelNum = 5,
            mobsProbability = listOf(Pair(cockroachFactory, 0.3F), Pair(ratFactory, 0.9F)),
            lootProbability = listOf(),     // todo
            maxBosses = 1,
        )
    )


    private var map = com.kkdgames.core.map.Map(mapX, mapY, levels[0])
    private var previousDirection = Direction.none     // starting direction - after going through the door on the right it
    // should be right, so we know, that Player should be drawn on the left side


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
        player

        val cockroachFactory = CockroachFactory(assets, player, viewportHeight, viewportWidth)
        mobGroup.addActor(cockroachFactory.giveMob())

        stage.addActor(player)
        stage.addActor(mobGroup)
    }

    override fun render(delta: Float) {
        val backgroundTexture = assets.manager.get(Assets.background1)

        val wayTexture = assets.manager.get(Assets.wayTexture)

        Gdx.gl.glClearColor(0F, 0F, 0.2f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        game.batch.begin()
        game.batch.draw(backgroundTexture, viewportWidth / 2, viewportHeight / 2)
        game.batch.draw(wayTexture, viewportWidth / 2, 0F)
        game.batch.draw(wayTexture, viewportWidth / 2, viewportHeight)
        game.batch.draw(wayTexture, 0F, viewportHeight / 2)
        game.batch.draw(wayTexture, viewportWidth, viewportHeight / 2)
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