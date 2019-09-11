package com.example.sqlite_lab

import android.content.Context
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

object DatabaseManagement {
    private lateinit var dataBase: PersonDatabase
    private var personList: List<Person>? = null

    fun initiateDBConnection(context: Context){
        dataBase = PersonDatabase.get(context)
    }

    fun addPersonToDB(name: String) {
         doAsync {
             val person = Person(0, name, 0)
             dataBase.personDao().insert(person)
        }
    }

    fun addTeamToDB(teamString: String, person: Person){

        doAsync {
            val team = Team(0, teamString)
            val teamID =  dataBase.teamDao().insert(team)
            person.teamid = teamID
            dataBase.personDao().update(person)
        }
    }

    fun modifyPersonTeam(person: Person){
        doAsync {
            dataBase.personDao().update(person)

        }
    }


    fun getCount(): Int? {
       return dataBase.personDao().getAll().value?.size
    }

    fun getPersonWithIndex(index: Int): Person?{
     return personList?.get(index)
    }

}