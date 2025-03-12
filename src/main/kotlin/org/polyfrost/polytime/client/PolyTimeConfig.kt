package org.polyfrost.polytime.client

import dev.deftu.omnicore.client.OmniKeyboard
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Keybind
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindManager.registerKeybind
import org.polyfrost.polytime.PolyTimeConstants
import org.polyfrost.polyui.input.KeybindHelper

object PolyTimeConfig : Config("${PolyTimeConstants.ID}.json", "/polytime_dark.svg", PolyTimeConstants.NAME, Category.QOL) { // TODO: Fix mod

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
    var enabled = true

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
    var forwardKeyBind = KeybindHelper.builder().keys(OmniKeyboard.KEY_RBRACKET).does { if (time < 24) time += 0.5f }.build()

    @Keybind(
        title = "Backward Key Bind",
        description = "Moves time backwards when pressed.",
    )
    var backwardKeybind = KeybindHelper.builder().keys(OmniKeyboard.KEY_LBRACKET).does { if (time > 0) time -= 0.5f }.build()

    init {
        addDependency("time", "IRL Time") { if (irlTime) Property.Display.DISABLED else Property.Display.SHOWN }

        registerKeybind(forwardKeyBind)
        addDependency("forwardKeyBind", "IRL Time") { if (irlTime) Property.Display.DISABLED else Property.Display.SHOWN }
        registerKeybind(backwardKeybind)
        addDependency("backwardKeybind", "IRL Time") { if (irlTime) Property.Display.DISABLED else Property.Display.SHOWN }
    }
}