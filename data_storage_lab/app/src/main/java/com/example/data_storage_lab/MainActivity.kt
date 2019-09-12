package com.example.data_storage_lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(), View.OnClickListener {

    val fManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        read_button.setOnClickListener(this)
        write_button.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
       if(view?.id == write_button.id || view?.id == read_button.id){
           fragmentChange(view)
       }
    }

    private fun fragmentChange(view: View?){
        val fTransaction = fManager.beginTransaction()
        val fragment : Fragment?

        fragment = when(view){
            write_button -> WriteFileFragment()
            read_button -> ReadFileFragment()
            else -> return
        }
        if(fManager.fragments.count() > 0){
            fTransaction.replace(R.id.fragment_container_main, fragment)
        }else{
            fTransaction.add(R.id.fragment_container_main, fragment)
        }
        fTransaction.commit()
    }




}
