package com.example.fragment_training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        happy_button.setOnClickListener(this)
        sad_button.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        println("click registered")
        when(view?.id){
            R.id.happy_button -> changeFragment(view)
            R.id.sad_button -> changeFragment(view)
        }
    }

   private fun changeFragment(view: View){
        val fManager = supportFragmentManager
        val fTransaction = fManager.beginTransaction()
        val fragment : Fragment

        if(view == happy_button){ fragment = Fragment01() }else{ fragment = Fragment02() }

        if(fManager.fragments.count() == 0){ fTransaction.add(R.id.fragment_container_main, fragment) }
        else{ fTransaction.replace(R.id.fragment_container_main, fragment) }

        fTransaction.commit()
        showSnackbar()
    }

    private fun showSnackbar(){
        println("snackbar now")
        val coordinator = this.findViewById<CoordinatorLayout>(R.id.coordinator_layout)
        Snackbar.make(coordinator, R.string.snackbar_base, Snackbar.LENGTH_LONG).show()

    }



}
