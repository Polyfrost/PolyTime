package org.polyfrost.polytime.command;

import org.polyfrost.polytime.PolyTime;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;

@Command(value = PolyTime.MODID, description = "Access the " + PolyTime.NAME + " GUI.", aliases = "timechanger")
public class TimeCommand {

    @Main
    private void main() {
        PolyTime.INSTANCE.config.openGui();
    }
}