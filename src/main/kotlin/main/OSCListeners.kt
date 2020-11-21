package main

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