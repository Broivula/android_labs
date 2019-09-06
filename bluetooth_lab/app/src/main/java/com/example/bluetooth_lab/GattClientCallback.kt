package com.example.bluetooth_lab

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import java.util.*

class GattClientCallback () : BluetoothGattCallback(){

    private lateinit var characteristic: BluetoothGattCharacteristic

    companion object {
        val HEART_RATE_SERVICE_UUID = convertFromInteger(0x180D)
        val HEART_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2A37)
        val CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902)

        private fun convertFromInteger(i: Int): UUID {
            val MSB = 0x0000000000001000L
            val LSB = -0x7fffff7fa064cb05L
            val value = (i and -0x1).toLong()
            return UUID(MSB or (value shl 32), LSB)
        }
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        if(status == BluetoothGatt.GATT_FAILURE){
            println(LOG_MSG +" connection failed.")
        }else{
            println( LOG_MSG + "succesfully connected!")
        }
        if(newState == BluetoothProfile.STATE_CONNECTED){
            println(LOG_MSG + " Connected GATT service.")
            gatt?.discoverServices()
        }
        else if(newState == BluetoothProfile.STATE_DISCONNECTED){
            println(LOG_MSG + " GATT service disconnecting.")
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)
        if(status != BluetoothGatt.GATT_SUCCESS){
            println(LOG_MSG + " service was disconnected, lol")
            return
        }

        for( service in gatt!!.services ){

            if( service.uuid == HEART_RATE_SERVICE_UUID){
                println(LOG_MSG + " found heart rate service!!")

                for(serviceChars in service.characteristics){
                    println(LOG_MSG + " found charasteristic ${serviceChars.uuid}")

                    characteristic = service.getCharacteristic(HEART_RATE_MEASUREMENT_CHAR_UUID)
                    gatt.setCharacteristicNotification(characteristic, true)

                }
            }
        }

    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
        super.onCharacteristicChanged(gatt, characteristic)
        println("char changed??")
        println(LOG_MSG + "${characteristic?.value}")
    }


}