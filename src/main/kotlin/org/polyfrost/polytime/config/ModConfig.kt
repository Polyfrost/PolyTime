package org.polyfrost.polytime.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneKeyBind
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.libs.universal.UKeyboard
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

    //@Switch(
    //    name = "Fast Time",
    //    category = "Time"
    //)
    var fastTime = false
        get() = false

    //@Slider(
    //    name = "Speed",
    //    min = 0.1f, max = 10f,
    //    instant = true,
    //    category = "Time"
    //)
    var fastSpeed = 1f
        get() = field.coerceIn(0.1f..10f)

    @KeyBind(
        name = "Forward Key Bind",
        description = "Moves time forwards when pressed.",
        category = "Time"
    )
    var forwardKeyBind: OneKeyBind = OneKeyBind(UKeyboard.KEY_RBRACKET)

    @KeyBind(
        name = "Backward Key Bind",
        description = "Moves time backwards when pressed.",
        category = "Time"
    )
    var backwardKeybind: OneKeyBind = OneKeyBind(UKeyboard.KEY_LBRACKET)

    init {
        initialize()
        addDependency("time", "IRL Time") { !irlTime }

        //addDependency("irlTime", "Fast Time") { !fastTime }
        //addDependency("time", "IRL Time / Fast Time") { !irlTime && !fastTime }
        //addDependency("fastTime", "IRL Time") { !irlTime }
        //addDependency("fastSpeed", "IRL Time / Fast Time") { !irlTime && fastTime }

        registerKeyBind(forwardKeyBind) { if (time < 24) time += 0.5f; }
        addDependency("forwardKeyBind", "IRL Time") { !irlTime }
        registerKeyBind(backwardKeybind) { if (time > 0) time -= 0.5f; }
        addDependency("backwardKeybind", "IRL Time") { !irlTime }
    }
}