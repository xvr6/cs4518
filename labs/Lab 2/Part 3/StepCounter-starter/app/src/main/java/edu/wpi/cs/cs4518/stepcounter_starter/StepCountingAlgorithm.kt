package edu.wpi.cs.cs4518.stepcounter_starter

import android.util.Log
import kotlin.math.sqrt

object StepCounterAlgorithm {
    private const val TAG = "StepCounterAlgorithm"

    // Step detection algorithm based on magnitude
    fun detectSteps(sensorData: List<FloatArray>): Int {
        // Calculate the magnitude of acceleration for each sample
        val magnitudes =
                sensorData.map { sample ->
                    sqrt(sample[0] * sample[0] + sample[1] * sample[1] + sample[2] * sample[2])
                }

       // Detect peaks in the magnitude data
        val smoothedMagnitudes = smoothData(magnitudes)

        var stepCount = 0
        for (i in 1 until smoothedMagnitudes.size - 1) {
            if (smoothedMagnitudes[i] > smoothedMagnitudes[i - 1] &&
                smoothedMagnitudes[i] > smoothedMagnitudes[i + 1] &&
                smoothedMagnitudes[i] > 1.4
            ) {
                stepCount++
            }
        }

        Log.d(TAG, "Step detection completed. Steps detected: $stepCount")
        return stepCount
    }

    // Helper function to apply a moving average filter
    private fun smoothData(data: List<Float>, windowSize: Int = 5): List<Float> {
        if (data.size < windowSize) return data // Not enough data to smooth

        val smoothedData = mutableListOf<Float>()
        for (i in data.indices) {
            val start = maxOf(0, i - windowSize / 2)
            val end = minOf(data.size, i + windowSize / 2 + 1)
            val window = data.subList(start, end)
            smoothedData.add(window.average().toFloat())
        }
        return smoothedData
    }
}
