package com.example.file_explorer_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val rootFolder = Environment.getExternalStorageDirectory()
    private var previousFolder : File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //so first let's check the folder
        if(checkPermission())
        getFiles(rootFolder)
        disableFAB()
        recycler_view.layoutManager = GridLayoutManager(this, 3)

        back_button.setOnClickListener { view ->
            getFiles(previousFolder?.parentFile!!)
        }

    }

    fun getFiles(file: File){
        previousFolder = file
        if(file != rootFolder){
            back_button.show()
            back_button.isEnabled = true
        }else{
            disableFAB()
        }
        setTitle(file.path)
        recycler_view.adapter = MainAdapter(file.listFiles(), this)
    }

    fun previewFile(file:File){
        val activityIntent = Intent(this, FilePreviewActivity::class.java)
        activityIntent.putExtra("file", file)
        startActivity(activityIntent)
    }

    private fun checkPermission(): Boolean{
        requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        return true
    }
    private fun disableFAB(){
        back_button.hide()
        back_button.isEnabled = false
    }
}
