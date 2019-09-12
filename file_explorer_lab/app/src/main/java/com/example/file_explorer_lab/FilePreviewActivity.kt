package com.example.file_explorer_lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_file_preview.*
import java.io.File

class FilePreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_preview)

        val file = intent?.extras?.get("file") as File
        filename_text.text = file.name
        val type = parseFileNameType(file.name)

        when(type){
            "txt" -> { disableImage(); file_text.text = file.readText()}
            "jpg", "png" -> { preview_image.setImageURI(file.path.toUri()) }
        }

        file_text.text = file.readText()
    }

    private fun disableImage(){
        preview_image.setImageResource(0)
    }

    private fun parseFileNameType(filename: String) :String{
        return filename.split(".")[1]
    }
}
