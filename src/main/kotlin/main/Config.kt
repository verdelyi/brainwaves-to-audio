package main

import java.io.FileInputStream
import java.util.*

object Config {

    private val prop = Properties()

    init {
        prop.load(FileInputStream("config.properties"))
    }

    val playerSamplingRate: Double = prop.getProperty("player.samplingrate").toDouble()
    val udpPort = prop.getProperty("network.udpport").toInt()
}