package com.kkdgames.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.kkdgames.core.MainGame

object DesktopLauncher {

    @JvmStatic
    fun main(args: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()
        config.setTitle("The Pickle Rick Experience")
        config.setWindowSizeLimits(1280, 720, -1, -1)
        config.useVsync(true)

        Lwjgl3Application(MainGame(), config)
    }
}
