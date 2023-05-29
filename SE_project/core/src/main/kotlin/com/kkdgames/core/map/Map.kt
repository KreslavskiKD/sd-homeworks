package com.kkdgames.core.map

import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.utils.Array

class Map(
    private val width : Int,
    private val height : Int,
    levelDescription: LevelDescription,
    private val viewportWidth: Int,
    private val viewportHeight: Int,
) {

    // from top to bottom from left to right
    // 1.1 1.2 1.3 ... 1.w
    // 2.1 2.2 2.3 ... 2.w
    // ... ... ...
    // h.1 h.2 h.3 ... h.w
    val mapRooms = Array<Array<Room>>()
    val centerH = height / 2
    val centerW = width / 2

    var finishH = random.nextInt(height)
    var finishW = random.nextInt(width)

    init {
        // add all rooms
        for (i in 0 until height) {
            mapRooms.add(Array(width))
            for (j in 0 until width) {
                mapRooms[i].add(Room())
            }
        }

        // generate finish room
        var retry = 0
        while (finishH == centerH && retry < 10) {
            finishH = random.nextInt(height)
            retry++
        }
        if (finishH == centerH && retry == 10) {
            finishH = centerH + 1
        }

        retry = 0
        while (finishW == centerW && retry < 10) {
            finishW = random.nextInt(width)
            retry++
        }
        if (finishW == centerW && retry == 10) {
            finishW = centerW + 1
        }

        // mark start room
        mapRooms[centerH][centerW].start = true
        // mark finish room
        mapRooms[finishH][finishW].finish = true

        // mark boss rooms
        for (i in 0 until levelDescription.maxBosses) {
            var h = random.nextInt(height)
            retry = 0
            while (h == centerH && retry < 10) {
                h = random.nextInt(height)
                retry++
            }
            if (h == centerH && retry == 10) {
                h = centerH + 1
            }

            var w = random.nextInt(width)
            retry = 0
            while (w == centerW && retry < 10) {
                w = random.nextInt(width)
                retry++
            }
            if (w == centerW && retry == 10) {
                w = centerW + 1
            }

            mapRooms[h][w].boss = true
        }

        // add loot
        for (i in 0 until height) {
            for (j in 0 until width) {
                for (loot in levelDescription.lootProbability) {
                    val proba = random()
                    if (proba <= loot.second) {
                        mapRooms[i][j].loot.add(loot.first.giveLoot(
                            random.nextFloat() * viewportHeight,
                            random.nextFloat() * viewportWidth,
                        ))
                    }
                }
            }
        }

        // add mobs
        for (i in 0 until height) {
            for (j in 0 until width) {
                for (mob in levelDescription.mobsProbability) {
                    val proba = random()
                    if (proba <= mob.second) {
                        mapRooms[i][j].mobs.add(mob.first.giveMob())
                    }
                }
            }
        }

        // generate paths

        // generate some path from start to the finish
        dfs(centerH, centerW)

        // maybe we should make some more paths?

    }

    private fun dfs(i: Int, j: Int): Boolean {
        if (i == finishH && j == finishW) {
            return true
        }

        mapRooms[i][j].visited = true

        val directions = listOf(Direction.top, Direction.bottom, Direction.left, Direction.right).shuffled()
        for (direction in directions) {
            when (direction) {
                Direction.top -> {
                    if (i > 0 && !mapRooms[i - 1][j].visited) {
                        mapRooms[i][j].top = mapRooms[i - 1][j]
                        mapRooms[i - 1][j].bottom = mapRooms[i][j]
                        dfs(i - 1, j)
                    }
                }
                Direction.bottom -> {
                    if (i < height - 1 && !mapRooms[i + 1][j].visited) {
                        mapRooms[i][j].bottom = mapRooms[i + 1][j]
                        mapRooms[i + 1][j].top = mapRooms[i][j]
                        dfs(i + 1, j)
                    }
                }
                Direction.left -> {
                    if (j > 0 && !mapRooms[i][j - 1].visited) {
                        mapRooms[i][j].left = mapRooms[i][j - 1]
                        mapRooms[i][j - 1].right = mapRooms[i][j]
                        dfs(i, j - 1)
                    }
                }
                Direction.right -> {
                    if (j < width - 1 && !mapRooms[i][j + 1].visited) {
                        mapRooms[i][j].right = mapRooms[i][j + 1]
                        mapRooms[i][j + 1].left = mapRooms[i][j]
                        dfs(i, j + 1)
                    }
                }
                Direction.none -> { // bruh wtf it ain't possible
                    return false
                }
            }
        }
        return false
    }
}
