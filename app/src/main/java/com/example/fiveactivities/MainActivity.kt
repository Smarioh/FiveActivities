package com.example.fiveactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Switch
import androidx.core.view.GestureDetectorCompat

class MainActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

                // Determine which direction had the largest difference in movement
                if (Math.abs(diffX) > Math.abs(diffY)) {
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


}
