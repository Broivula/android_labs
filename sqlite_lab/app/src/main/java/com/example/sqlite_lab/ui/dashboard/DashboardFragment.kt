package com.example.sqlite_lab.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sqlite_lab.DatabaseManagement
import com.example.sqlite_lab.LOG_TEXT
import com.example.sqlite_lab.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        root.add_button_dash.setOnClickListener { view ->
            if(person_name_text.text.length > 0 && person_name_text.text.length < 25){
                //there was a legit name, add it to the database.
                DatabaseManagement.addPersonToDB(person_name_text.text.toString())
                person_name_text.text.clear()
            }
        }

        return root
    }


}