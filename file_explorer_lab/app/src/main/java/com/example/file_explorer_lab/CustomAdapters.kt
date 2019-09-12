package com.example.file_explorer_lab

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.directory_layout.view.*
import java.io.File

class MainAdapter(var directory: Array<File>, var activity: MainActivity): RecyclerView.Adapter<CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val element = layoutInflater.inflate(R.layout.directory_layout, parent, false)
        return CustomViewHolder(element)
    }

    override fun getItemCount(): Int {
        //the amount of files AND directories contained inside a folder
       return directory.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val temp = directory.get(position)

        if(temp.isDirectory){
            holder.view.imageButton.setImageResource(R.drawable.empty_folder)
        }else if(parseFileNameType(temp.name) == "jpg" || parseFileNameType(temp.name) == "png"){
            holder.view.imageButton.setImageURI(temp.absoluteFile.toUri())
        }else{
            holder.view.imageButton.setImageResource(R.drawable.file_picture)
        }

        holder.view.dir_name_text.text = temp.name

        holder.view.imageButton.setOnClickListener {view ->
            println("KIKKEL getting files from.." + temp.name)
            if(temp.isDirectory) activity.getFiles(temp)
            else { activity.previewFile(temp) }
        }
    }

    private fun parseFileNameType(filename: String) :String{
        return filename.split(".")[1]
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)