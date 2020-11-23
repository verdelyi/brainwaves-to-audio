package main

import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import java.io.PrintWriter

class RawEEGSignalGenerator : SignalGenerator {
    private var buffer: MutableList<Double> = mutableListOf()
    private var pos = 0
    private val limit = 1024

    fun addSample(s: Double) {
        buffer.add(s)
        println("Buffer size: ${buffer.size} ")
        if (buffer.size >= limit) {
            buffer = buffer.takeLast(limit).toMutableList()
            /*val fftTransformer = FastFourierTransformer(DftNormalization.STANDARD)
            val fft = fftTransformer.transform(buffer.toDoubleArray(), TransformType.FORWARD)

            PrintWriter("fft.txt").use { pw ->
                fft.forEach {
                    pw.println("${it.real},${it.imaginary}}")
                }
            }
            println("Wrote FFT")*/
        }
    }

    override fun generateSignal(): Double {
        pos++
        if (pos >= buffer.size) {
            pos = 0
        }
        if (buffer.isNotEmpty())
            return buffer[pos]
        else {
            //println("RawEEGSignalGenerator: cannot generate signal; No data")
            return 0.0
        }
    }

}