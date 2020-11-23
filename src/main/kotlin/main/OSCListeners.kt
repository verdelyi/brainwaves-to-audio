package main

import java.io.PrintWriter
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class GammaListener : BrainwaveOSCListener(
    tag = "[Gamma]",
    symbol = "G",
    oscPattern = "/muse/elements/gamma_absolute"
) {
    override fun updateSound(amp: Double) {
        signalGenerator.amplitude = amp
        signalGenerator.currentFreq = 349.23
    }
}

class BetaListener : BrainwaveOSCListener(
    tag = "[Beta ]",
    symbol = "B",
    oscPattern = "/muse/elements/beta_absolute"
) {
    override fun updateSound(amp: Double) {
        signalGenerator.amplitude = amp
        signalGenerator.currentFreq = 293.66
    }
}

class AlphaListener : BrainwaveOSCListener(
    tag = "[Alpha]",
    symbol = "A",
    oscPattern = "/muse/elements/alpha_absolute"
) {
    override fun updateSound(amp: Double) {
        signalGenerator.amplitude = amp
        signalGenerator.currentFreq = 220.0
    }
}

class ThetaListener : BrainwaveOSCListener(
    tag = "[Theta]",
    symbol = "T",
    oscPattern = "/muse/elements/theta_absolute"
) {
    override fun updateSound(amp: Double) {
        signalGenerator.amplitude = amp
        signalGenerator.currentFreq = 174.61
    }
}

class DeltaListener : BrainwaveOSCListener(
    tag = "[Delta]",
    symbol = "D",
    oscPattern = "/muse/elements/delta_absolute"
) {
    override fun updateSound(amp: Double) {
        signalGenerator.amplitude = amp
        signalGenerator.currentFreq = 146.83
    }
}

class BlinkListener : BaseOSCListener(oscPattern = "/muse/elements/blink") {

    protected val signalGenerator: SineGenerator = SineGenerator(
        amplitude = 0.1, currentFreq = 440.0, sampleRate = Config.playerSamplingRate
    )

    override fun initialize() {
        thread { SoundPlayer(signalGenerator).Start() }
    }

    override fun update() {
        val arg = messageArgumentsAsString
        if (arg != null) {
            check(arg.size == 1 && arg[0] == "1")
            signalGenerator.currentFreq = 7000.0
            signalGenerator.amplitude = 1.0
        } else {
            signalGenerator.amplitude = 0.0
        }
    }
}

class JawClenchListener : BaseOSCListener(oscPattern = "/muse/elements/jaw_clench") {

    protected val signalGenerator: SineGenerator = SineGenerator(
        amplitude = 0.1, currentFreq = 440.0, sampleRate = Config.playerSamplingRate
    )

    override fun initialize() {
        thread { SoundPlayer(signalGenerator).Start() }
    }

    override fun update() {
        val arg = messageArgumentsAsString
        if (arg != null) {
            check(arg.size == 1 && arg[0] == "1")
            signalGenerator.currentFreq = 6000.0
            signalGenerator.amplitude = 1.0
        } else {
            signalGenerator.amplitude = 0.0
        }
    }
}

class TouchingForeheadListener : BaseOSCListener(oscPattern = "/muse/elements/touching_forehead") {

    private val signalGenerator: SineGenerator = SineGenerator(
        amplitude = 0.1, currentFreq = 440.0, sampleRate = Config.playerSamplingRate
    )

    override fun initialize() {
        thread { SoundPlayer(signalGenerator).Start() }
    }

    override fun update() {
        val arg = messageArgumentsAsString
        if (arg != null) {
            check(arg.size == 1)
            when (arg[0]) {
                "1" -> signalGenerator.amplitude = 0.0
                "0" -> {
                    signalGenerator.currentFreq = 5000.0
                    signalGenerator.amplitude = 1.0
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class HorseShoeListener : BaseOSCListener(oscPattern = "/muse/elements/horseshoe") {

    private val signalGenerator: SineGenerator = SineGenerator(
        amplitude = 0.1, currentFreq = 440.0, sampleRate = Config.playerSamplingRate
    )

    override fun initialize() {
        thread { SoundPlayer(signalGenerator).Start() }
    }

    override fun update() {
        val arg = messageArgumentsAsDouble
        if (arg != null) {
            check(arg.size == 4)
            if (arg.all { it == 1.0 }) { // All 4 good fit
                signalGenerator.amplitude = 0.0
            } else {
                signalGenerator.currentFreq = 5000.0
                signalGenerator.amplitude = 1.0
                println(arg.contentToString())
            }
        }
    }
}

class RawEEGListener : BaseOSCListener(oscPattern = "/muse/eeg") {
    //private val signalGenerator = RawEEGSignalGenerator()
    private var counter = 0

    private val pw = PrintWriter("raweeg.txt").apply {
        println("TP9, AF7, AF8, TP10, AUX")
    }

    override fun initialize() {
        /*thread {
            try {
                SoundPlayer(signalGenerator).Start()
            } catch (e: Exception) {
                e.printStackTrace()
                exitProcess(1)
            }
        }*/
    }

    override fun update() {
        val arg = messageArgumentsAsDouble
        if (arg != null) {
            check(arg.size >= 4)
            //val sample = (arg[0] - 800) / 900.0
            //signalGenerator.addSample(sample)
            pw.println(arg.joinToString())
            println("[Count $counter] ${arg.contentToString()}")
            counter++
        }
    }
}