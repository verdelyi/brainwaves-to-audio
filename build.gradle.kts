plugins {
    kotlin("jvm") version "1.4.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application.mainClass.set("main.Main")

dependencies {
    implementation("com.illposed.osc:javaosc-core:0.7")
    implementation("org.apache.commons:commons-math3:3.6.1")
}

val fatJar = task("fatJar", type = Jar::class) {
    group = "build"
    manifest.attributes["Main-Class"] = application.mainClass
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks.withType<Wrapper> { gradleVersion = "6.7.1" }