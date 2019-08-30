package com.example.kotlinadapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create the adapter to convert the array to views
        val adapter = PresidentListAdapter(this, GlobalModel.presidents)

        // use a custom layout (instead of the ListActivity default layout)
        setContentView(R.layout.activity_main)

        // attach the adapter to a ListView
        mainlistview.adapter = adapter

        mainlistview.setOnItemClickListener { _, _, position, _ ->
            Log.d("USR", "Selected $position")
            selname.text = GlobalModel.presidents[position].toString()
            seldescription.text = GlobalModel.presidents[position].description

            callWebService(GlobalModel.presidents[position].toString())


        }

        mainlistview.setOnItemLongClickListener { _, _, position, _ ->
            val selectedPresident = GlobalModel.presidents[position]
            val detailIntent = PresidentDetailActivity.newIntent(this, selectedPresident)

            startActivity(detailIntent)
            true
        }

    }
    private fun callWebService (president: String){


        val call = NetworkOperations.service.hitCountCheck("query", "json", "search",president)
        val value = object : Callback<NetworkOperations.Model.Result> {
            override fun onResponse(call: Call<NetworkOperations.Model.Result>, response: Response<NetworkOperations.Model.Result>?){
                if(response != null){
                    val res : NetworkOperations.Model.Result? = response.body()
                    val hits =  getString(R.string.search_hits) +  res?.query?.searchinfo?.totalhits.toString()
                    total_hits.text = hits
                }
            }
            override fun onFailure(call: Call<NetworkOperations.Model.Result>, t:Throwable){
                println("error with search: " + t.toString())
            }
        }

        call.enqueue(value)
    }

    private inner class PresidentListAdapter(context: Context, private val presidents: MutableList<President>) : BaseAdapter() {
        private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return presidents.size
        }

        override fun getItem(position: Int): Any {
            return presidents[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.item_president, parent, false)

            val thisPresident = presidents[position]
            var tv = rowView.findViewById(R.id.tvName) as TextView
            tv.text = thisPresident.name

            tv = rowView.findViewById(R.id.tvStartDuty) as TextView
            tv.text = Integer.toString(thisPresident.startDuty)

            tv = rowView.findViewById(R.id.tvEndDuty) as TextView
            tv.text = Integer.toString(thisPresident.endDuty)

            return rowView
        }
    }
}
