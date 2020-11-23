package main

import com.illposed.osc.OSCMessage
import com.illposed.osc.OSCMessageEvent
import com.illposed.osc.OSCMessageListener
import java.time.Instant
import kotlin.concurrent.thread

abstract class BaseOSCListener(
    val oscPattern: String
) : OSCMessageListener {

    var timeStamp: Instant? = null
        private set
    var message: OSCMessage? = null
        private set

    override fun acceptMessage(event: OSCMessageEvent) {
        timeStamp = event.time.toInstant()
        message = event.message
    }

    private val messageArguments: Array<Any>?
        get() {
            var arguments: Array<Any>? = null
            if (message != null) {
                arguments = message!!.arguments.toTypedArray()
                message = null
            }
            return arguments
        }

    val messageArgumentsAsDouble: DoubleArray?
        get() {
            val arguments = messageArguments
            return if (arguments != null && arguments.isNotEmpty()) {
                arguments.map { it.toString().toDouble() }.toDoubleArray()
            } else null
        }

    val messageArgumentsAsString: Array<String?>?
        get() {
            val arguments = messageArguments
            return if (arguments != null && arguments.isNotEmpty()) {
                arguments.map { it.toString() }.toTypedArray()
            } else null
        }

    abstract fun initialize()

    abstract fun update()
}

