package com.example.camera_lab_01

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    var mCurrentPhotoPath : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        camera_button.setOnClickListener { view ->
            val fileName = "temp_photo"
            val imgPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            var imageFile: File? = null
            imageFile = File.createTempFile(fileName, ".jpg", imgPath)
            mCurrentPhotoPath = imageFile!!.absolutePath
            val photoURI: Uri = FileProvider.getUriForFile(this, "com.example.camera_lab_01", imageFile)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(intent.resolveActivity(packageManager) != null){
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, 1)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
        val exifInterface = ExifInterface(mCurrentPhotoPath)
        val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        camera_image.setImageBitmap(bitmap as Bitmap?)
        when(orientation){
            ExifInterface.ORIENTATION_ROTATE_270 ->   {camera_image.rotation = 270F; println("vertical yes?")}
            ExifInterface.ORIENTATION_ROTATE_90 -> {camera_image.rotation = 90F; println("horizontal no?")}
        }

    }
}
