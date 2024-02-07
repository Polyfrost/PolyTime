package org.polyfrost.polytime.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.data.*
import org.polyfrost.polytime.PolyTime


object ModConfig : Config(Mod(PolyTime.NAME, ModType.UTIL_QOL, "/polytime_dark.svg"), "${PolyTime.MODID}.json") {

    @Info(
        text = "Credits to Fyu for the original TimeChanger mod.",
        type = InfoType.INFO,
        size = 2,
        category = "Time"
    )
    private var credits = false

    @Checkbox(
        name = "Use IRL time",
        category = "Time"
    )
    var irlTime = false

    @Checkbox(
        name = "Use IRL lunar phase",
        category = "Time"
    )
    var irlLunarPhases = false

    @Slider(
        name = "Time",
        min = 0f, max = 24f,
        instant = true,
        category = "Time"
    )
    var time = 12f

    @Switch(
        name = "Fast Time",
        category = "Time"
    )
    var fastTime = false

    @Slider(
        name = "Speed",
        min = 0.1f, max = 10f,
        instant = true,
        category = "Time"
    )
    var fastSpeed = 1f
        get() = field.coerceIn(0.1f..10f)

    init {
        initialize()

        addDependency("irlTime", "") { !fastTime }
        addDependency("time", "") { !irlTime && !fastTime }
        addDependency("fastTime", "") { !irlTime }
        addDependency("fastSpeed", "") { !irlTime && fastTime }
    }
}