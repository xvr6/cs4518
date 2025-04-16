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
        var stepCount = 0
        for (i in 1 until magnitudes.size - 1) {
            if (magnitudes[i] > magnitudes[i - 1] &&
                            magnitudes[i] > magnitudes[i + 1] &&
                            magnitudes[i] > 1.2
            ) {
                stepCount++
            }
        }

        Log.d(TAG, "Step detection completed. Steps detected: $stepCount")
        return stepCount
    }
}
