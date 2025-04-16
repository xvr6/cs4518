package edu.wpi.cs.cs4518.stepcounter_starter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.ConcurrentLinkedQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {

    // Live data
    private val _stepCount = MutableLiveData(0)
    val stepCount: LiveData<Int> = _stepCount

    // Thread-safe queue to store sensor data
    private val sensorDataQueue = ConcurrentLinkedQueue<FloatArray>()

    // Add sensor data to the queue and process when 50 samples are accumulated
    fun addSensorData(x: Float, y: Float, z: Float) {
        sensorDataQueue.add(floatArrayOf(x, y, z))
        if (sensorDataQueue.size >= 50) {
            processSensorData()
        }
    }

    // Process sensor data and update step count
    private fun processSensorData() {
        CoroutineScope(Dispatchers.Default).launch {
            // Pull 50 samples from the queue
            val sensorDataBatch = mutableListOf<FloatArray>()
            repeat(50) { sensorDataQueue.poll()?.let { sensorDataBatch.add(it) } }

            // Pass the data to the step detection algorithm
            val detectedSteps = StepCounterAlgorithm.detectSteps(sensorDataBatch)

            // Update the step count on the main thread
            CoroutineScope(Dispatchers.Main).launch {
                _stepCount.value = (_stepCount.value ?: 0) + detectedSteps
            }
        }
    }

    companion object {
        private const val TAG = "CounterViewModel"
    }
}
