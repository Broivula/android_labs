package com.example.bluetooth_lab

import android.app.Activity
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.le.ScanResult
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bluetooth_result_row.view.*
import java.util.logging.Handler

class MainAdapter (val activity: MainActivity) : RecyclerView.Adapter<CustomViewHolder>(), BleWrapper.BleCallback {


    private lateinit var mHandler: BleWrapper


    override fun onDeviceReady(gatt: BluetoothGatt) {
        println(LOG_MSG + " device is ready")
        mHandler.getNotifications(gatt, mHandler.HEART_RATE_SERVICE_UUID, mHandler.HEART_RATE_MEASUREMENT_CHAR_UUID)

    }

    override fun onDeviceDisconnected() {
        println(LOG_MSG + " devide disconnected")

    }

    override fun onNotify(characteristic: BluetoothGattCharacteristic) {
       // println("hey there mate, message for you: ")
        updateUI(characteristic.value[1].toInt())

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.bluetooth_result_row, parent, false)
        return CustomViewHolder(cellRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val tempResult = DataManager.mScanResults?.values?.toList()?.get(position)
        val bgrCol = if(tempResult!!.isConnectable){Color.GREEN}else{Color.RED}

        holder.view.setBackgroundColor(bgrCol)
        holder.view.name_text.text = tempResult?.scanRecord?.deviceName ?: "name not found"
        holder.view.address_text.text = tempResult?.device?.address ?: "address not found"
        holder.view.rssi_text.text = tempResult?.rssi.toString() + " dBm"

        holder.view.setOnClickListener { view ->
            println("clicked this button!")
          //  tempResult.device.connectGatt(holder.view.context, false, GattClientCallback())

            mHandler = BleWrapper(holder.view.context, tempResult.device.address)
            mHandler.addListener(this)
            mHandler.connect(false)

        }
    }

    override fun getItemCount(): Int {
        return DataManager.mScanResults?.size!!.toInt()
    }

    private fun updateUI(heartRate: Int){
        activity.runOnUiThread {
            activity.heart_rate_text.text = heartRate.toString()
        }
    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)