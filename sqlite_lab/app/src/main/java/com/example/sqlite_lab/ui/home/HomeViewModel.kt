package com.example.sqlite_lab.ui.home

import android.app.Application
import android.app.Person
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sqlite_lab.DatabaseManagement
import com.example.sqlite_lab.PersonDatabase
import com.example.sqlite_lab.PersonTeamInfo

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val personList: LiveData<List<com.example.sqlite_lab.Person>> = PersonDatabase.get(getApplication()).personDao().getAll()
    private val personTeamList: LiveData<List<PersonTeamInfo>> = PersonDatabase.get(getApplication()).personDao().getPersonTeamInfo()

    fun getPersons() = personList

    fun getPersonTeamInfo() = personTeamList
    /*
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
     */
}