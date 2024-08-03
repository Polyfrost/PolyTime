package org.polyfrost.polytime.config


import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polytime.PolyTime


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

    init {
        // initialize()
        addDependency("time", "IRL Time") { !irlTime }
        //addDependency("irlTime", "Fast Time") { !fastTime }
        //addDependency("time", "IRL Time / Fast Time") { !irlTime && !fastTime }
        //addDependency("fastTime", "IRL Time") { !irlTime }
        //addDependency("fastSpeed", "IRL Time / Fast Time") { !irlTime && fastTime }
    }
}