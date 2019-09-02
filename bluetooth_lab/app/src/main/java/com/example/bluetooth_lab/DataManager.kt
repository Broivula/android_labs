package com.example.bluetooth_lab

import android.bluetooth.le.ScanResult

object DataManager {

     var mScanResults: LinkedHashMap<String, ScanResult>? = null

    fun setScanResults(results: LinkedHashMap<String, ScanResult>? ){
        mScanResults = results
    }
}