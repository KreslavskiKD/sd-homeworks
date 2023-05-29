package com.kkdgames.core.loot

import com.kkdgames.core.loot.heals.SaladFactory
import com.kkdgames.core.loot.upgrades.CockroachBodyFactory
import com.kkdgames.core.loot.weapons.CockroachLegFactory
import com.kkdgames.core.loot.weapons.DrillFactory

object Factories {
    lateinit var saladFactory : SaladFactory

    lateinit var drillFactory : DrillFactory

    lateinit var cockroachLegFactory : CockroachLegFactory

    lateinit var cockroachBodyFactory : CockroachBodyFactory
}