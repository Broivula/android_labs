package com.example.data_storage_lab


import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_read_file.*
import kotlinx.android.synthetic.main.fragment_write_file.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class ReadFileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkPermissions()
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME)
            read_text_view.text = file.readText()
        }
    }

    private fun checkPermissions(){
        requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }
}
