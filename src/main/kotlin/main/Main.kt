package main

import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector
import com.illposed.osc.transport.udp.OSCPortIn

// Mind Monitor OSC spec: https://mind-monitor.com/FAQ.php#oscspec
object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Listening for OSC on: UDP/${Config.udpPort}")
        println("Sound output sampling rate: ${Config.playerSamplingRate} Hz")

        val oscReceiver = OSCPortIn(Config.udpPort)
        val listeners = listOf(
            GammaListener(),
            BetaListener(),
            AlphaListener(),
            ThetaListener(),
            DeltaListener(),
            RawEEGListener(), // EXPERIMENTAL, NOT USEFUL YET
            BlinkListener(), JawClenchListener(),
            HorseShoeListener(), TouchingForeheadListener()
        )

        // Initialize and register all listeners
        listeners.forEach { listener ->
            oscReceiver.dispatcher.addListener(OSCPatternAddressMessageSelector(listener.oscPattern), listener)
            listener.initialize()
        }

        oscReceiver.startListening()
        while (true) {
            listeners.forEach { it.update() }
        }
        oscReceiver.stopListening()
    }
}