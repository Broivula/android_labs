package com.example.bluetooth_lab

import android.bluetooth.le.ScanResult

object DataManager {

    var mScanResults: LinkedHashMap<String, ScanResult>? = null
    var heartRateMeasurements: Array<Int> = arrayOf()


    fun setScanResults(results: LinkedHashMap<String, ScanResult>? ){
        mScanResults = results
    }

    fun addMeasurement(value: Int){
        heartRateMeasurements += value
    }
    fun getLowest(): Int{
        return heartRateMeasurements.sorted()[0]
    }
    fun getHighest(): Int{
        return heartRateMeasurements.sorted()[heartRateMeasurements.size - 1]
    }
}