package main

import kotlin.concurrent.thread
import kotlin.math.pow
import kotlin.math.roundToInt

abstract class BrainwaveOSCListener(
    val tag: String,
    val symbol: String,
    oscPattern: String
) : BaseOSCListener(oscPattern) {

    protected val signalGenerator: SineGenerator = SineGenerator(
        amplitude = 0.1, currentFreq = 440.0, sampleRate = Config.playerSamplingRate
    )

    private fun bars(n: Double, symbol: String): String {
        val convertedAmplitude = ((n + 5) * 7).roundToInt()
        return (1..convertedAmplitude).joinToString("") { symbol }
    }

    override fun initialize() {
        thread { SoundPlayer(signalGenerator).Start() }
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