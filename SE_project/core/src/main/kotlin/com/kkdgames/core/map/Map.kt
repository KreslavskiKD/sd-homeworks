package com.kkdgames.core.map

import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.utils.Array

class Map(
    width : Int,
    height : Int,
    levelDescription: LevelDescription,
) {

    private val mapRooms = Array<Array<Room>>()
    private val centerH = height / 2
    private val centerW = width / 2

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
                        mapRooms[i][j].loot.add(loot.first.giveLoot())
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

    }

}