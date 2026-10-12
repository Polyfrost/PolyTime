# PolyTime

[![Modrinth downloads](https://img.shields.io/modrinth/dt/m3kNp1iP?style=flat&logo=modrinth&label=Modrinth&color=1bd96a)](https://modrinth.com/mod/polytime)
[![CurseForge downloads](https://img.shields.io/curseforge/dt/1148000?style=flat&logo=curseforge&label=CurseForge&color=f16436)](https://www.curseforge.com/minecraft/mc-mods/polytime)

PolyTime sets the time of day you see in your world. It only changes your own view, so the server and other players keep their own time.

## Features

- Pick a time on a 24-hour slider, in half-hour steps. 12 is noon and 0 is midnight.
- Press `]` to move the time forward by half an hour and `[` to move it back. You can rebind both keys.
- Follow the real time where you live. PolyTime maps your local sunrise, noon and sunset onto the Minecraft day, so the sun rises in-game when it rises outside.
- Show the real moon phase for today's date.

## Supported versions

- Minecraft 1.8.9 on Ornithe.
- Minecraft 1.21.1, 1.21.4, 1.21.5, 1.21.8, 1.21.10, 1.21.11, 26.1, 26.2 and 26.3 on Fabric.

PolyTime needs [OneConfig](https://modrinth.com/mod/oneconfig) and Fabric Language Kotlin.

## Support

- Report bugs and ask questions on the [Polyfrost Discord](https://polyfrost.org/discord).
- Check [Polyfrost Status](https://status.polyfrost.org/) if a Polyfrost service is down.

## Building

```sh
./gradlew ":26.3:build"
```

Replace `26.3` with any supported Minecraft version. The build needs JDK 21 and JDK 25 installed.
