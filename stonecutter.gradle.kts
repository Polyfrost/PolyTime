plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.3" /* [SC] DO NOT EDIT */

stonecutter {
    tasks {
        order("publishModrinth")
    }

    parameters {
        replacements {
            string(eval(current.version, "= 1.8.9")) {
                replace(
                    "com.mojang.blaze3d.platform.InputConstants",
                    "org.polyfrost.oneconfig.internal.legacy.InputConstants"
                )
            }
        }
    }
}
