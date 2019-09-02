package com.example.bluetooth_lab

import android.bluetooth.le.ScanResult
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bluetooth_result_row.view.*

class MainAdapter (activity: MainActivity) : RecyclerView.Adapter<CustomViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return DataManager.mScanResults?.size!!.toInt()
    }


}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)