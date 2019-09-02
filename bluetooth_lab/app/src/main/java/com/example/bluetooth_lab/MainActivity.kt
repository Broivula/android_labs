package com.example.bluetooth_lab

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mHandler: Handler? = null
    private var mScanCallback : ScanCallback? = null
    private var mBluetootLeScanner : BluetoothLeScanner? = null
    private var mScanning : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scan_button.text = "start scan"


        scan_button.setOnClickListener { view ->
            startScan()
        }

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        recyclerView_main.layoutManager = LinearLayoutManager(this)

    }

    private var mScanResults: LinkedHashMap<String, ScanResult>? = null

    private fun startScan() {
        if(!hasPermissions() || mScanning) return
        DataManager.mScanResults?.clear()
        if(recyclerView_main.adapter != null){(recyclerView_main.adapter as RecyclerView.Adapter).notifyDataSetChanged()}


        scan_button.text = "stop scan"
        println( LOG_MSG +" scan started.")
        mScanResults = LinkedHashMap()
        mScanCallback = BtleScanCallBack()
        mBluetootLeScanner = mBluetoothAdapter!!.bluetoothLeScanner

        val settings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build()
        val filter: List<ScanFilter>? = null

        mHandler = Handler()
        mHandler!!.postDelayed({stopScan()}, SCAN_PERIOD)

        mScanning = true
        mBluetootLeScanner!!.startScan(filter, settings, mScanCallback)

    }

    private fun stopScan(){
        mBluetootLeScanner?.stopScan(mScanCallback)
        mScanCallback = null
        mScanning = false
        mHandler = null
        println(LOG_MSG + " scanning stopped.")
        scanComplete()
    }

    private inner class BtleScanCallBack: ScanCallback(){
        override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult) {
            addScanResult(result)
        }

        override fun onScanFailed(errorCode: Int) {
            println(LOG_MSG + " scan failed, errorcode : " + errorCode.toString())
        }
        private fun addScanResult(result: ScanResult){
            val device = result.device
            val deviceAddress = result.device.address
            mScanResults!![deviceAddress] = result
            println(LOG_MSG + " found device: $device with address ")

        }

    }

    private fun hasPermissions() : Boolean {
        if(mBluetoothAdapter == null || !mBluetoothAdapter!!.isEnabled){
            println(LOG_MSG + " no bluetooth LE capability.")
            return false
        }else if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            println(LOG_MSG + " no fine location access.")
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return true
        }
        return true
    }

    private fun scanComplete () {
        scan_button.text = "start scan"
        if(mScanResults!!.count() > 0){ DataManager.setScanResults(mScanResults);   recyclerView_main.adapter = MainAdapter(this)}else{ showSnackbar()}
    }

    private fun showSnackbar(){
        println("snackbar now")
        val coordinator = this.findViewById<CoordinatorLayout>(R.id.coordinator_layout)
        Snackbar.make(coordinator, "no devices found", Snackbar.LENGTH_LONG).show()

    }

}
