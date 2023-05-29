package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
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
import com.kkdgames.core.screens.Constants.LEVEL
import com.kkdgames.core.util.Assets
import kotlin.system.exitProcess

class GameScreen(private val game: MainGame, private val assets: Assets) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()
    private var stage: Stage

    private val viewportWidth = Gdx.graphics.width
    private val viewportHeight = Gdx.graphics.height

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

    private val gates: com.badlogic.gdx.utils.Array<Gate>

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

        mobGroup = Group()

        setupStage()

        val wayTexture = assets.manager.get(Assets.wayTexture)
        val lowerGate = Gate(wayTexture, Gate.Type.LOWER, viewportWidth, viewportHeight)
        val upperGate = Gate(wayTexture, Gate.Type.UPPER, viewportWidth, viewportHeight)
        val leftGate = Gate(wayTexture, Gate.Type.LEFT, viewportWidth, viewportHeight)
        val rightGate = Gate(wayTexture, Gate.Type.RIGHT, viewportWidth, viewportHeight)

        gates = Array(4)
        gates.add(leftGate, rightGate, lowerGate, upperGate)
    }

    private fun setupPlayer(): Player {
        val playerTexture = assets.manager.get(Assets.playerFirstStageTexture)
        return Player(
            playerTexture,
            assets.manager.get(Assets.biteSound),
            viewportHeight / 5f,
            viewportWidth / 2 - (playerTexture.width / 2F),
            viewportHeight / 2 - (playerTexture.height / 2F),
        )
    }

    private fun setupStage() {
        stage.clear()
        if (LEVEL == DIFFICULTY.EASY) {
            mobGroup.clear()
        }
        for (mob in map.mapRooms[curX][curY].mobs) {
            mobGroup.addActor(mob)
        }

        stage.addActor(player)
        stage.addActor(mobGroup)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0.2f, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()

        game.batch.draw(
            backgroundTexture,
            0f,
            0f,
            viewportWidth.toFloat(),
            viewportHeight.toFloat(),
        )

        //game.batch.draw(inventoryTexture, 0f, 0f)

        gates.forEach {
            it.draw(game.batch)
        }

        game.batch.end()

        stage.act()
        updateMobsTarget()
        updatePlayerTarget()
        trackPlayerAndGates()
        stage.draw()

        if (player.health <= 0) {
            game.screen = DeathScreen(game, assets)
            dispose()
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose()
            exitProcess(0)
        }
    }

    private fun trackPlayerAndGates() {
        gates.forEach {
            if (player.centerX <= it.right() && player.centerX >= it.left()
                && player.centerY <= it.top() && player.centerY >= it.bottom()) {
                goToNewMap(it)
                return
            }
        }
    }

    private fun goToNewMap(gate: Gate) {
        when (gate.type) {
            Gate.Type.LEFT ->  {
                curX--
                //player.setPosition(viewportWidth - player.width, viewportHeight / 2f - player.height / 2)
            }
            Gate.Type.UPPER -> {
                curY++
                //player.setPosition(viewportWidth / 2 - player.width / 2, 0f)
            }
            Gate.Type.LOWER -> {
                curY--
                //player.setPosition(viewportWidth / 2 - player.width / 2, viewportHeight - player.height)
            }
            Gate.Type.RIGHT -> {
                curX++
                //player.setPosition(0f, viewportHeight / 2f - player.height / 2)
            }
            null -> {}
        }

        val maxX = map.mapRooms.size - 1
        val maxY = map.mapRooms[0].size - 1

        curX %= maxX
        curY %= maxY
        if (curX < 0) {
            curX = maxX
        }
        if (curY < 0) {
            curY = maxY
        }
        player.setPosition(player.originX, player.originY)
        setupStage()
    }

    private fun updateMobsTarget() {
        for (mob: Actor in mobGroup.children) { // maybe we want to add several mobs
            if (mob is Mob) {
                mob.setTargetPos(player.centerX, player.centerY)
            }
        }
    }

    private fun updatePlayerTarget() {
        var closestMob: Mob? = null
        var distMinCur = 1000000F
        for (mob: Actor in mobGroup.children) { // maybe we want to add several mobs
            if (mob is Mob) {
                val distCur = (mob.x - player.x) * (mob.x - player.x) + (mob.y - player.y) * (mob.y - player.y)
                if (distCur < distMinCur) {
                    distMinCur = distCur
                    closestMob = mob
                }
            }
        }
        player.setTarget(closestMob)
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