package org.polyfrost.polytime.config

import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Keybind
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindHelper
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindManager.registerKeybind
import org.polyfrost.polytime.PolyTime
import org.polyfrost.universal.UKeyboard

object ModConfig : Config("${PolyTime.MODID}.json", "/polytime_dark.svg", PolyTime.NAME, Category.QOL) {

    // TODO
    // @Info(
    //     text = "Credits to Fyu for the original TimeChanger mod.",
    //     type = InfoType.INFO,
    //     size = 2,
    //     category = "Time"
    // )
    // private var credits = false

    @Switch(
        title = "Enabled",
    )
    var enabled = false

    @Checkbox(
        title = "Use IRL time",
    )
    var irlTime = false

    @Checkbox(
        title = "Use IRL lunar phase",
    )
    var irlLunarPhases = false

    @Slider(
        title = "Time",
        min = 0f, max = 24f,
    )
    var time = 12f

    // @Switch(
    //     name = "Fast Time",
    //     category = "Time"
    // )
    // var fastTime = false
    //     get() = false

    // @Slider(
    //     name = "Speed",
    //     min = 0.1f, max = 10f,
    //     instant = true,
    //     category = "Time"
    // )
    // var fastSpeed = 1f
    //     get() = field.coerceIn(0.1f..10f)

    @Keybind(
        title = "Forward Key Bind",
        description = "Moves time forwards when pressed.",
    )
    var forwardKeyBind = KeybindHelper.builder().keys(UKeyboard.KEY_RBRACKET).does { if (time < 24) time += 0.5f }.register()

    @Keybind(
        title = "Backward Key Bind",
        description = "Moves time backwards when pressed.",
    )
    var backwardKeybind = KeybindHelper.builder().keys(UKeyboard.KEY_LBRACKET).does { if (time > 0) time -= 0.5f }.register()

    init {
        // initialize()
        addDependency("time", "IRL Time") { !irlTime }
        //addDependency("irlTime", "Fast Time") { !fastTime }
        //addDependency("time", "IRL Time / Fast Time") { !irlTime && !fastTime }
        //addDependency("fastTime", "IRL Time") { !irlTime }
        //addDependency("fastSpeed", "IRL Time / Fast Time") { !irlTime && fastTime }

        registerKeybind(forwardKeyBind)
        addDependency("forwardKeyBind", "IRL Time") { !irlTime }
        registerKeybind(backwardKeybind)
        addDependency("backwardKeybind", "IRL Time") { !irlTime }
    }
}