package com.github.theholychicken.managers

import com.github.theholychicken.config.DropsConfig

object ItemDropParser {
    var dropsConfig: DropsConfig = DropsConfig()

    fun reloadConfig() {
        dropsConfig.loadConfig()
    }

    fun initConfig() {
        dropsConfig.loadConfig()
    }
}