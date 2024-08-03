plugins {
    kotlin("jvm") version "1.9.10" apply false
    id("org.polyfrost.multi-version.root")
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

preprocess {
    val fabric10809 = createNode("1.8.9-fabric", 10809, "yarn")
    val fabric11202 = createNode("1.12.2-fabric", 11202, "yarn")
    val fabric11605 = createNode("1.16.5-fabric", 11605, "yarn")
    val fabric11701 = createNode("1.17.1-fabric", 11701, "yarn")
    val fabric11801 = createNode("1.18.1-fabric", 11801, "yarn")

    val forge10809 = createNode("1.8.9-forge", 10809, "srg")
    val forge11202 = createNode("1.12.2-forge", 11202, "srg")
    val forge11605 = createNode("1.16.5-forge", 11605, "srg")
    val forge11701 = createNode("1.17.1-forge", 11701, "srg")
    val forge11801 = createNode("1.18.1-forge", 11801, "yarn")

    fabric10809.link(forge10809)
    fabric11202.link(fabric10809)
    fabric11605.link(fabric11202)
    fabric11701.link(fabric11605)
    fabric11801.link(fabric11701)

    forge11202.link(fabric11202)
    forge11605.link(fabric11605)
    forge11701.link(fabric11701)
    forge11801.link(fabric11801)
}