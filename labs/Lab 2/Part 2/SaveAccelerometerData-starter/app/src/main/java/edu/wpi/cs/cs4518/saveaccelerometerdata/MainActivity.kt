package edu.wpi.cs.cs4518.saveaccelerometerdata

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), SensorEventListener {
	private var sensorManager: SensorManager? = null
	private var linearAccelerometer: Sensor? = null
	private var accelerometer: Sensor? = null
	private var linearFileWriter: FileWriter? = null
	private var accelFileWriter: FileWriter? = null
	private lateinit var statusTextView: TextView
	private lateinit var startButton: Button
	private lateinit var stopButton: Button
	private var isRecording = false

	private val desiredFrequencyHz = 50
	private val samplingPeriodUs = 1_000_000 / desiredFrequencyHz

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		statusTextView = findViewById(R.id.statusTextView)
		startButton = findViewById(R.id.startButton)
		stopButton = findViewById(R.id.stopButton)

		stopButton.isEnabled = false

		startButton.setOnClickListener {
			initialize()
		}

		stopButton.setOnClickListener {
			stopRecording()
		}
	}

	private fun initialize() {
		sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
		linearAccelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
		accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

		// Register both sensors
		linearAccelerometer?.let {
			sensorManager?.registerListener(this, it, samplingPeriodUs)
		}
		accelerometer?.let {
			sensorManager?.registerListener(this, it, samplingPeriodUs)
		}

		// debug statement to ensure they are registered
		if (linearAccelerometer != null) {
			Log.d("MainActivity", "Linear Accelerometer registered")
		}
		if (accelerometer != null) {
			Log.d("MainActivity", "Accelerometer registered")
		}

		try {
			// Create CSV files for both sensors
			val linearFile = createCSVFile("LinearAccelerometer")
			val accelFile = createCSVFile("Accelerometer")
			linearFileWriter = FileWriter(linearFile)
			accelFileWriter = FileWriter(accelFile)

			// Write headers to both files
			linearFileWriter?.append("Timestamp,X,Y,Z\n")
			accelFileWriter?.append("Timestamp,X,Y,Z\n")

			isRecording = true
			statusTextView.text = "Recording..."
			startButton.isEnabled = false
			stopButton.isEnabled = true
		} catch (e: IOException) {
			e.printStackTrace()
			Toast.makeText(this, "Failed to create files", Toast.LENGTH_SHORT).show()
		}
	}

	private fun stopRecording() {
		if (isRecording) {
			sensorManager?.unregisterListener(this)
			try {
				linearFileWriter?.close()
				accelFileWriter?.close()
				Toast.makeText(this, "Recordings saved", Toast.LENGTH_SHORT).show()
			} catch (e: IOException) {
				e.printStackTrace()
				Toast.makeText(this, "Failed to save files", Toast.LENGTH_SHORT).show()
			}
			isRecording = false
			statusTextView.text = "Recording stopped"
			startButton.isEnabled = true
			stopButton.isEnabled = false
		}
	}

	@Throws(IOException::class)
	private fun createCSVFile(sensorType: String): File {
		val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
		val fileName = "${sensorType}_Data_$timestamp.csv"
		val storageDir =
			Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
		if (!storageDir.exists()) {
			storageDir.mkdirs()
		}

		val file = File(storageDir, fileName)
		Log.d("MainActivity", "File saved at: ${file.absolutePath}")

		return file
	}

	override fun onSensorChanged(event: SensorEvent) {
		if (isRecording) {
			val currentTimestamp = System.currentTimeMillis()
			val x = event.values[0]
			val y = event.values[1]
			val z = event.values[2]

			try {
				when (event.sensor.type) {
					Sensor.TYPE_LINEAR_ACCELERATION -> {
						// Write linear accelerometer data to its file
						linearFileWriter?.append(
							String.format(
								Locale.getDefault(),
								"%d,%.3f,%.3f,%.3f\n",
								currentTimestamp,
								x,
								y,
								z
							)
						)
					}
					Sensor.TYPE_ACCELEROMETER -> {
						// Write accelerometer data to its file
						accelFileWriter?.append(
							String.format(
								Locale.getDefault(),
								"%d,%.3f,%.3f,%.3f\n",
								currentTimestamp,
								x,
								y,
								z
							)
						)
					}
				}
			} catch (e: IOException) {
				e.printStackTrace()
			}
		}
	}

	override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
		// Not used
	}

	override fun onDestroy() {
		super.onDestroy()
		stopRecording()
	}
}