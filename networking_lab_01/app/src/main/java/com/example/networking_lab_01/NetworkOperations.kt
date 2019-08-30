package com.example.networking_lab_01

import java.io.InputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import android.os.Handler

class NetworkOperations(mHandler : Handler) : Runnable{
    val myHandler = mHandler

    override fun run() {
        try {
            val myUrl = URL("http://13.ip-51-75-16.eu:2222/get/registeredItems")
            val myConn = myUrl.openConnection() as HttpURLConnection
            myConn.requestMethod = "GET"

            val istream : InputStream = myConn.inputStream
            val allText = istream.bufferedReader().use {
                it.readText()
            }

            val result  = StringBuilder()
            result.append(allText).toString()

            val msg = myHandler.obtainMessage()
            msg.what = 0
            msg.obj = result
            myHandler.sendMessage(msg)

        }catch (e: Exception){
            println("something went wrong with networking operations, error message: ")
            println(e.toString())
        }
    }
}