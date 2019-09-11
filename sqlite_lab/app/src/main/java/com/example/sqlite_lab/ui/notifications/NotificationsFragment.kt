package com.example.sqlite_lab.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sqlite_lab.DatabaseManagement
import com.example.sqlite_lab.LOG_TEXT
import com.example.sqlite_lab.Person
import com.example.sqlite_lab.R
import com.example.sqlite_lab.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var selectedPersonList: List<Person>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        homeViewModel.getPersons().observe(this, Observer {
            selectedPersonList = it
           root.registered_persons_spinner.adapter = ArrayAdapter<Person>(root.context, R.layout.support_simple_spinner_dropdown_item, it)
        })

        root.add_button_team.setOnClickListener { view ->
            if(team_text_edit.text.length > 0 && team_text_edit.text.length < 20){
                println(LOG_TEXT + selectedPersonList.get(root.registered_persons_spinner.selectedItemPosition).fullname)
                DatabaseManagement.addTeamToDB(team_text_edit.text.toString(), selectedPersonList.get(root.registered_persons_spinner.selectedItemPosition))
                team_text_edit.text.clear()
            }
        }

        return root
    }
}