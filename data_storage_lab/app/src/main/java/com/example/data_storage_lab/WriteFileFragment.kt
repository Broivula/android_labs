package com.example.data_storage_lab

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_write_file.*
import java.io.File
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class WriteFileFragment() : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        save_button.setOnClickListener{view -> checkPermissions()
            if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
                    writeToAFile()
            }
        }
    }
    private fun writeToAFile(){
        println(LOG_TEXT + " writing to a file now..")
        var textToBeSaved = write_edit_text.text.toString() + "\n"

        try {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME)
            file.appendText(textToBeSaved)
            textToBeSaved = ""
            write_edit_text.text.clear()
        }catch (e: Exception){
            println(LOG_TEXT + " something went wrong with writing to the file. error msg: ")
            println(e.toString())
        }

        println("file should be saved by now!")
    }
    private fun checkPermissions(){
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

}
