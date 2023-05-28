package com.kkdgames.core.map

import com.badlogic.gdx.scenes.scene2d.Actor
import com.kkdgames.core.loot.Loot

// if a direction reference is null than there is no door there and no way
class Room {
    var left : Room? = null
    var top : Room? = null
    var right : Room? = null
    var bottom : Room? = null
    val loot: MutableList<Loot> = mutableListOf()
    val mobs: MutableList<Actor> = mutableListOf()
    var finish : Boolean = false
    var start : Boolean = false
    var boss: Boolean = false
    var visited: Boolean = false
}

enum class Direction {
    top,
    bottom,
    right,
    left,
    none,
}
