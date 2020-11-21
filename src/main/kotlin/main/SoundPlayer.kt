package main

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine


class SoundPlayer(val siggen: SineGenerator) {
    var sndOut: SourceDataLine? = null

    fun Start() {
        try {
            sndOut!!.start()
            val bufSize = 8000
            while (true) {
                val sig = (1..bufSize).map { siggen.generateSignal() }
                val sigBytes = sig.map { (it * 127).toInt().toByte() }.toByteArray()
                sndOut!!.write(sigBytes, 0, sigBytes.size)
            }
            sndOut!!.drain()
            sndOut!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        //  specifying the audio format
        val _format = AudioFormat(
            Config.playerSamplingRate.toFloat(),  // Sample Rate
            8,  // Size of SampleBits
            1,  // Number of Channels
            true,  // Is Signed?
            false // Is Big Endian?
        )

        //  creating the DataLine Info for the speaker format
        val speakerInfo = DataLine.Info(SourceDataLine::class.java, _format)

        //  getting the mixer for the speaker
        sndOut = AudioSystem.getLine(speakerInfo) as SourceDataLine
        sndOut!!.open(_format)
    }
}