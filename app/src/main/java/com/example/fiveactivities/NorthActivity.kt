package com.example.fiveactivities

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.math.sqrt

class NorthActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_north)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    private var initialY: Float = 0f
    private var initialX: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                initialY = event.y
                initialX = event.x
            }
            MotionEvent.ACTION_UP -> {
                val finalY = event.y
                val finalX = event.x

                // Calculate differences
                val diffY = initialY - finalY
                val diffX = initialX - finalX

                if (abs(diffX) > abs(diffY)) {
                    if (diffX > 0) {
                        startActivity(Intent(this, WestActivity::class.java))
                    } else {
                        startActivity(Intent(this, EastActivity::class.java))
                    }
                } else {
                    if (diffY > 0) {
                        startActivity(Intent(this, NorthActivity::class.java))
                    } else {
                        startActivity(Intent(this, SouthActivity::class.java))
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private var lastShakeTime: Long = 0

    private val sensorListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val acceleration = sqrt((x * x + y * y + z * z).toDouble()) - SensorManager.GRAVITY_EARTH
                val now = System.currentTimeMillis()

                if (acceleration > 1 && now - lastShakeTime > 2000) {
                    shakeScreen()
                    lastShakeTime = now
                }
            }
        }
    }
    private fun shakeScreen() {
        val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake_animation)
        findViewById<TextView>(R.id.northTextView).startAnimation(shake)
    }
    override fun onResume() {
        super.onResume()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(sensorListener)
    }

}