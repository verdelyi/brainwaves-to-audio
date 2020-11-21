package main

import kotlin.math.pow
import kotlin.math.roundToInt

abstract class BrainwaveOSCListener(
    val tag: String,
    val symbol: String,
    oscPattern: String
) : BaseOSCListener(oscPattern) {

    private fun bars(n: Double, symbol: String): String {
        val convertedAmplitude = ((n + 5) * 7).roundToInt()
        return (1..convertedAmplitude).joinToString("") { symbol }
    }

    override fun update() {
        val value = messageArgumentsAsDouble
        if (value != null) {
            val dB = value[0] * 10
            val amp = 10.0.pow(dB / 20) / 6
            updateSound(amp)
            println("${tag}: ${bars(dB, symbol)} @ $dB dB")
        }
    }

    protected abstract fun updateSound(amp: Double)
}