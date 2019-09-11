package com.example.sqlite_lab.ui.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_lab.DatabaseManagement
import com.example.sqlite_lab.Person
import com.example.sqlite_lab.PersonTeamInfo
import com.example.sqlite_lab.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.name_row.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.getPersonTeamInfo().observe(this, Observer {
            person_recycler_view.adapter = PersonListAdapater(it?.sortedBy { it.person?.fullname })
        })

        root.person_recycler_view.layoutManager = LinearLayoutManager(root.context)
        return root
    }
}

class PersonListAdapater(val personList: List<PersonTeamInfo>?) : RecyclerView.Adapter<CustomViewHolder>(){
    override fun getItemCount(): Int {
        return personList?.count() ?: 0
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.name_text.text = personList?.get(position)?.person?.fullname ?: "name not found"
        holder.view.team_text.text = personList?.get(position)?.team?.team_name ?: "team not assigned."
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.name_row, parent, false)
        return CustomViewHolder(cellRow)
    }
}
class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)