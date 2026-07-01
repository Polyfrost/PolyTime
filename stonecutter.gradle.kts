plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "1.21.10" /* [SC] DO NOT EDIT */
stonecutter {
    parameters {
        replacements {
            string(eval(current.version, "= 1.8.9")) {
                replace(
                    "com.mojang.blaze3d.platform.InputConstants",
                    "org.polyfrost.oneconfig.internal.legacy.InputConstants"
                )
                replace(
                    "net.minecraft.server.Bootstrap",
                    "net.minecraft.Bootstrap"
                )
            }
        }
    }
}
