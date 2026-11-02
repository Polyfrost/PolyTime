package org.polyfrost.polytime.client

import com.mojang.blaze3d.platform.InputConstants
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Keybind
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindHelper
import org.polyfrost.polytime.PolyTimeConstants
import org.polyfrost.polytime.client.realtime.RealTimeHandler

object PolyTimeConfig : Config("${PolyTimeConstants.ID}.json", "/assets/polytime/polytime_dark.svg", PolyTimeConstants.NAME, Category.QOL) {
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
    ) @JvmStatic var isEnabled = false

    @Checkbox(
        title = "Use IRL time",
    ) @JvmStatic
    var isIrlTime = false

    @Checkbox(
        title = "Use IRL lunar phase",
    ) @JvmStatic
    var irlLunarPhases = false

    @Dropdown(
        title = "Moon phase",
        options = ["Default", "Full Moon", "Waning Gibbous", "Last Quarter", "Waning Crescent", "New Moon", "Waxing Crescent", "First Quarter", "Waxing Gibbous"],
    )
    var moonPhase = 0

    @JvmStatic
    val moonPhaseOverride: Int
        get() = if (irlLunarPhases) RealTimeHandler.currentLunarPhase else moonPhase - 1

    @Slider(
        title = "Time",
        min = 0f, max = 24f, step = 0.5f
    )
    var time = 12f

    @Checkbox(
        title = "Fast Time",
    )
    var fastTime = false

    @Slider(
        title = "Speed",
        min = 0.1f, max = 100f, step = 0.1f
    )
    var fastSpeed = 10f

    @Keybind(
        title = "Forward Key Bind",
        description = "Moves time forwards when pressed.",
    )
    var forwardKeyBind = KeybindHelper.builder().key(InputConstants.KEY_RBRACKET).action(Runnable { if (time < 24) time += 0.5f }).register()

    @Keybind(
        title = "Backward Key Bind",
        description = "Moves time backwards when pressed.",
    )
    var backwardKeybind = KeybindHelper.builder().key(InputConstants.KEY_LBRACKET).action(Runnable { if (time > 0) time -= 0.5f }).register()

    init {
        eventHandler { _: TickEvent.End ->
            if (timeDirection != 0) time = (time + timeDirection * keybindSpeed / 20f).coerceIn(0f, 24f)
        }

        addDependency("moonPhase", "IRL Lunar Phase") { if (irlLunarPhases) Property.Display.DISABLED else Property.Display.SHOWN }

        addDependency("time", "IRL Time") { if (isIrlTime) Property.Display.DISABLED else Property.Display.SHOWN }
        addDependency("time", "Fast Time") { if (fastTime) Property.Display.DISABLED else Property.Display.SHOWN }
        addDependency("fastTime", "IRL Time") { if (isIrlTime) Property.Display.DISABLED else Property.Display.SHOWN }
        addDependency("fastSpeed", "fastTime")

        addDependency("forwardKeyBind", "IRL Time") { if (isIrlTime) Property.Display.DISABLED else Property.Display.SHOWN }
        addDependency("backwardKeybind", "IRL Time") { if (isIrlTime) Property.Display.DISABLED else Property.Display.SHOWN }

        addCallback<Boolean>("isIrlTime") { enabled ->
            if (enabled) RealTimeHandler.populateTime()
            false
        }
    }
}
