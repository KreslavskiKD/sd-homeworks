package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kkdgames.core.MainGame
import com.kkdgames.core.map.Direction
import com.kkdgames.core.map.LevelDescription
import com.kkdgames.core.mobs.Mob
import com.kkdgames.core.mobs.cockroach.CockroachFactory
import com.kkdgames.core.mobs.rat.RatFactory
import com.kkdgames.core.models.Gate
import com.kkdgames.core.models.Player
import com.kkdgames.core.screens.Constants.MAX_HEIGHT
import com.kkdgames.core.screens.Constants.MAX_WIDTH
import com.kkdgames.core.util.Assets

class GameScreen(private val game: MainGame, private val assets: Assets) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()
    private var stage: Stage

    private val viewportWidth = MAX_WIDTH
    private val viewportHeight = MAX_HEIGHT

    private val mapX = 5
    private val mapY = 5

    private var curX = 2
    private var curY = 2

    private var player: Player = setupPlayer()

    private var mobGroup: Group

    private val backgroundTexture: Texture = assets.manager.get(Assets.background1)
    private val inventoryTexture: Texture = assets.manager.get(Assets.inventory)

    private val passiveCockroachFactory = CockroachFactory(
        assets = assets,
        viewportHeight = viewportHeight,
        viewportWidth = viewportWidth,
        player = player,
        strategy = Mob.Companion.Strategies.PASSIVE,
    )

    private val aggressiveCockroachFactory = CockroachFactory(
        assets = assets,
        viewportHeight = viewportHeight,
        viewportWidth = viewportWidth,
        player = player,
        strategy = Mob.Companion.Strategies.ATTACKING,
    )

    private val ratFactory = RatFactory(
        assets = assets,
        viewportHeight = viewportHeight,
        viewportWidth = viewportWidth,
        player = player,
        strategy = Mob.Companion.Strategies.ATTACKING,
    )

    private val levels = listOf(
        LevelDescription(
            levelNum = 1,
            mobsProbability = listOf(
                Pair(passiveCockroachFactory, 0.4F),
                Pair(aggressiveCockroachFactory, 0.05F),
                Pair(ratFactory, 0.1F),
            ),
            lootProbability = listOf(),     // todo
            maxBosses = 1,
        ),
        LevelDescription(
            levelNum = 2,
            mobsProbability = listOf(
                Pair(passiveCockroachFactory, 0.4F),
                Pair(aggressiveCockroachFactory, 0.05F),
                Pair(ratFactory, 0.4F),
            ),
            lootProbability = listOf(),     // todo
            maxBosses = 1,
        ),
        LevelDescription(
            levelNum = 3,
            mobsProbability = listOf(
                Pair(passiveCockroachFactory, 0.2F),
                Pair(aggressiveCockroachFactory, 0.6F),
                Pair(ratFactory, 0.4F),
            ),
            lootProbability = listOf(),     // todo
            maxBosses = 4,
        ),
        LevelDescription(
            levelNum = 5,
            mobsProbability = listOf(
                Pair(passiveCockroachFactory, 0.1F),
                Pair(aggressiveCockroachFactory, 0.3F),
                Pair(ratFactory, 0.9F),
            ),
            lootProbability = listOf(),     // todo
            maxBosses = 1,
        )
    )

    private var map = com.kkdgames.core.map.Map(mapX, mapY, levels[0])
    private var previousDirection = Direction.none     // starting direction - after going through the door on the right it
    // should be right, so we know, that Player should be drawn on the left side

    private val lowerGate: Gate
    private val upperGate: Gate
    private val leftGate: Gate
    private val rightGate: Gate

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

        val wayTexture = assets.manager.get(Assets.wayTexture)
        val gatesScreenHeight = backgroundTexture.height.toFloat()
        val offset = (viewportWidth - gatesScreenHeight) / 2

        lowerGate = Gate(wayTexture, Gate.Type.LOWER, viewportWidth, gatesScreenHeight, offset)
        upperGate = Gate(wayTexture, Gate.Type.UPPER, viewportWidth, gatesScreenHeight, offset)
        leftGate = Gate(wayTexture, Gate.Type.LEFT, viewportWidth, gatesScreenHeight, offset)
        rightGate = Gate(wayTexture, Gate.Type.RIGHT, viewportWidth, gatesScreenHeight, offset)
    }

    private fun setupPlayer(): Player {
        val playerTexture = assets.manager.get(Assets.playerFirstStageTexture)
        return Player(
            playerTexture,
            viewportHeight / 5f,
            viewportWidth / 2 - (playerTexture.width / 2F),
            viewportHeight / 2 - (playerTexture.height / 2F),
        )
    }

    private fun setupStage() {
        for (mob in map.mapRooms[curX][curY].mobs) {
            mobGroup.addActor(mob)
        }

        stage.addActor(player)
        stage.addActor(mobGroup)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0.2f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        game.batch.begin()

        game.batch.draw(
            inventoryTexture,
            (viewportWidth - inventoryTexture.width.toFloat()) / 2,
            backgroundTexture.height.toFloat(),
        )

        game.batch.draw(
            backgroundTexture,
            (viewportWidth - backgroundTexture.width.toFloat()) / 2,
            0f,
        )

        lowerGate.draw(game.batch)
        upperGate.draw(game.batch)
        leftGate.draw(game.batch)
        upperGate.draw(game.batch)

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