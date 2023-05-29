package com.kkdgames.core.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.kkdgames.core.MainGame
import com.kkdgames.core.loot.Loot
import com.kkdgames.core.loot.heals.SaladFactory
import com.kkdgames.core.loot.upgrades.CockroachBodyFactory
import com.kkdgames.core.loot.weapons.CockroachLegFactory
import com.kkdgames.core.loot.weapons.DrillFactory
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

    private var state = State.RUN

    private var player: Player = setupPlayer()

    private var mobGroup: Group
    private var lootOnTheFloor: MutableList<Rectangle>

    private val backgroundTexture: Texture = assets.manager.get(Assets.background1)
    private val inventoryTexture: Texture = assets.manager.get(Assets.inventory)
    private val inventoryHighlightedTexture: Texture = assets.manager.get(Assets.inventoryHighlight)

    private val lowerGate: Gate
    private val upperGate: Gate
    private val leftGate: Gate
    private val rightGate: Gate

    private var currentlySelectedItem = 0

    private val saladFactory = SaladFactory(
        assets = assets,
    )

    private val drillFactory = DrillFactory(
        assets = assets,
    )

    private val cockroachLegFactory = CockroachLegFactory(
        assets = assets,
    )

    private val cockroachBodyFactory = CockroachBodyFactory(
        assets = assets,
    )

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
            lootProbability = listOf(
                Pair(saladFactory, 0.4F),
            ),
            maxBosses = 1,
        ),
        LevelDescription(
            levelNum = 2,
            mobsProbability = listOf(
                Pair(passiveCockroachFactory, 0.4F),
                Pair(aggressiveCockroachFactory, 0.05F),
                Pair(ratFactory, 0.4F),
            ),
            lootProbability = listOf(
                Pair(saladFactory, 0.4F),
                Pair(drillFactory, 0.1F),
            ),
            maxBosses = 1,
        ),
        LevelDescription(
            levelNum = 3,
            mobsProbability = listOf(
                Pair(passiveCockroachFactory, 0.2F),
                Pair(aggressiveCockroachFactory, 0.6F),
                Pair(ratFactory, 0.4F),
            ),
            lootProbability = listOf(
                Pair(saladFactory, 0.4F),
                Pair(drillFactory, 0.1F),
            ),
            maxBosses = 4,
        ),
        LevelDescription(
            levelNum = 5,
            mobsProbability = listOf(
                Pair(passiveCockroachFactory, 0.1F),
                Pair(aggressiveCockroachFactory, 0.3F),
                Pair(ratFactory, 0.9F),
            ),
            lootProbability = listOf(
                Pair(saladFactory, 0.4F),
                Pair(drillFactory, 0.1F),
            ),
            maxBosses = 1,
        )
    )

    private var map = com.kkdgames.core.map.Map(
        mapX,
        mapY,
        levels[0],
        viewportHeight = viewportHeight,
        viewportWidth = viewportWidth,
    )

    private var currentRoom = map.mapRooms[curX][curY]

    private val gates: Array<Gate>

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
        lootOnTheFloor = mutableListOf()

        val wayTexture = assets.manager.get(Assets.wayTexture)
        lowerGate = Gate(wayTexture, Gate.Type.LOWER, viewportWidth, viewportHeight)
        upperGate = Gate(wayTexture, Gate.Type.UPPER, viewportWidth, viewportHeight)
        leftGate = Gate(wayTexture, Gate.Type.LEFT, viewportWidth, viewportHeight)
        rightGate = Gate(wayTexture, Gate.Type.RIGHT, viewportWidth, viewportHeight)

        gates = Array()
        setupStage()
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

        gates.clear()
        if (currentRoom.bottom != null) {
            gates.add(lowerGate)
        }
        if (currentRoom.left != null) {
            gates.add(leftGate)
        }
        if (currentRoom.right != null) {
            gates.add(rightGate)
        }
        if (currentRoom.top != null) {
            gates.add(upperGate)
        }

        for (mob in currentRoom.mobs) {
            if ((mob as Mob).health > 0) {
                mobGroup.addActor(mob)
            }
        }

        lootOnTheFloor.clear()
        for (loot in currentRoom.loot) {
            lootOnTheFloor.add(loot)
        }

        stage.addActor(player)
        stage.addActor(mobGroup)
    }

    override fun render(delta: Float) {

        when (state) {
            State.RUN -> {
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

                gates.forEach {
                    it.draw(game.batch)
                }

                val deletedLoot = mutableListOf<Loot>()

                lootOnTheFloor.forEach {
                    it as Loot
                    if (it.taken) {
                        deletedLoot.add(it)
                    }
                }

                lootOnTheFloor.removeAll(deletedLoot)

                lootOnTheFloor.forEach {
                    it as Loot
                    game.batch.draw(
                        it.getTexture(),
                        it.x,
                        it.y,
                    )
                }

                game.batch.end()

                stage.act()
                updateMobs()
                updateMobsTarget()
                updatePlayerTarget()
                updatePlayerClosestLoot()
                trackPlayerAndGates()
                stage.draw()

                if (player.health <= 0) {
                    game.screen = DeathScreen(game, assets)
                    dispose()
                }

                if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                    pause()

                }

                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    dispose()
                    exitProcess(0)
                }

            }
            State.PAUSE -> {
//                Gdx.gl.glClearColor(0F, 0F, 0.2f, 1F)
//                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
                camera.update()
                game.batch.projectionMatrix = camera.combined
                game.batch.begin()

                game.batch.draw(
                    inventoryTexture,
                    viewportWidth / 2 - (inventoryTexture.width / 2F),
                    viewportHeight / 2 - (inventoryTexture.height / 2F),
                )

                game.batch.draw(
                    inventoryHighlightedTexture,
                    viewportWidth / 2 - (inventoryTexture.width / 2F) + inventoryHighlightedTexture.width * currentlySelectedItem,
                    viewportHeight / 2 - (inventoryTexture.height / 2F),
                )

                var i = 0
                player.inventory.forEach {
                    game.batch.draw(
                        it.getTexture(),
                        viewportWidth / 2 - (inventoryTexture.width / 2F) + inventoryHighlightedTexture.width * i,
                        viewportHeight / 2 - (inventoryTexture.height / 2F),
                    )
                    i++
                }

                game.batch.end()

                if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                    currentlySelectedItem = 0
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                    currentlySelectedItem = 1
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                    currentlySelectedItem = 2
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
                    currentlySelectedItem = 3
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
                    currentlySelectedItem = 4
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
                    currentlySelectedItem = 5
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
                    currentlySelectedItem = 6
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
                    currentlySelectedItem = 7
                }

                if (Gdx.input.isKeyPressed(Input.Keys.U)) {
                    if (player.inventory.size >= currentlySelectedItem) {
                        player.inventory[currentlySelectedItem].equip()
                    }
                }

                if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
                    resume()
                }
            }
            State.RESUME -> {
                state = State.RUN
            }
            else -> {
                state = State.RUN
            }
        }
    }

    private fun updateMobs() {
        for (mob: Actor in mobGroup.children) { // maybe we want to add several mobs
            if (mob is Mob) {
                if (mob.health <= 0) {
                    val loot = mob.dropLoot()
                    currentRoom.loot.addAll(loot)
                    mobGroup.removeActor(mob)
                    // map.mapRooms[curX][curY].mobs.remove(mob)
                    currentRoom.mobs.remove(mob)
                }
            }
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
                currentRoom = currentRoom.left
                player.setPosition(viewportWidth - player.width - gate.width, viewportHeight / 2f - player.height / 2)
            }
            Gate.Type.UPPER -> {
                currentRoom = currentRoom.top
                player.setPosition(viewportWidth / 2 - player.width / 2, gate.height + player.height / 2)
            }
            Gate.Type.LOWER -> {
                currentRoom = currentRoom.bottom
                player.setPosition(viewportWidth / 2 - player.width / 2, viewportHeight - player.height / 2 - gate.height - 200)
            }
            Gate.Type.RIGHT -> {
                currentRoom = currentRoom.right
                player.setPosition(gate.width, viewportHeight / 2f - player.height / 2)
            }
            null -> {}
        }
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

    private fun updatePlayerClosestLoot() {
        var closestLoot: Loot? = null
        var distMinCur = 1000000F
        for (loot in lootOnTheFloor) {
            val distCur = (loot.x - player.x) * (loot.x - player.x) + (loot.y - player.y) * (loot.y - player.y)
            if (distCur < distMinCur) {
                distMinCur = distCur
                closestLoot = loot as Loot?
            }
        }
        player.setClosestLoot(closestLoot)
    }

    override fun resize(width: Int, height: Int) { }
    override fun hide() { }

    override fun pause() {
        this.state = State.PAUSE
    }

    override fun resume() {
        this.state = State.RESUME
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun dispose() {
        stage.dispose()
    }

    enum class State {
        PAUSE, RUN, RESUME, STOPPED
    }
}