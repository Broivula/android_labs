package com.example.sensor_lab

import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heartRate = HeartRate()
        heartRate.getHeartRate(getSystemService(Context.SENSOR_SERVICE) as SensorManager, this)

    }
}
