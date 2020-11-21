package main

import kotlin.math.PI
import kotlin.math.sin

class SineGenerator(
    var amplitude: Double,
    var currentFreq: Double,
    private val sampleRate: Double
) {
    var currentPos = 0.0

    fun generateSignal(): Double {
        currentPos += 2 * PI * (currentFreq / sampleRate)
        return amplitude * sin(currentPos)
    }
}

