package com.example.sensor_lab

import android.app.Activity
import android.content.pm.PackageManager
import android.hardware.*
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class HeartRate : SensorEventListener {


    private lateinit var sensorManager: SensorManager
    private lateinit var activity: Activity
    var sHRSensor : Sensor? = null

    fun getHeartRate(sm:  SensorManager, ac: Activity){
        activity = ac
        if(checkPermission()){
            sensorManager = sm
            if(sm.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null){
                println("KIKKEL found sensor")
                sHRSensor = sm.getDefaultSensor(Sensor.TYPE_HEART_RATE)
                sensorManager.registerListener(this, sHRSensor, 3 )
                activity.runOnUiThread {
                    activity.accuracy_text.text = activity.getText(R.string.initial_text)
                }
            }
        }

    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        if(p0 == sHRSensor){
            activity.runOnUiThread {
                activity.accuracy_text.text = when(p1){
                    0-> activity.getText(R.string.acc_0)
                    1-> activity.getText(R.string.acc_1)
                    2-> activity.getText(R.string.acc_2)
                    3-> activity.getText(R.string.acc_3)
                    else -> activity.getText(R.string.acc_weird)
                }
            }
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if(p0?.sensor == sHRSensor){
            activity.runOnUiThread {
                val hr = p0!!.values[p0!!.values.count() - 1].toInt()
                activity.heartRate_text.text = hr.toString()
            }


        }
    }

    private fun checkPermission() : Boolean{
        if((Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(activity!!.baseContext, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.BODY_SENSORS), 0)
            return true
        }else{
            println("permission not set.")
        }
        return true
    }
}