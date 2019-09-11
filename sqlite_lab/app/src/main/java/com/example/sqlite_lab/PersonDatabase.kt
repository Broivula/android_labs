package com.example.sqlite_lab

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    val uid: Long,
    @ColumnInfo(name = "Person_name")
    val fullname: String,
    @ColumnInfo(name = "teamid")
    @ForeignKey(entity = Team::class,parentColumns = ["teamid"], childColumns = ["teamuid"] )
    var teamid: Long){
    override fun toString(): String = fullname
}

@Entity
data class Team(
    @PrimaryKey(autoGenerate = true)
    val teamid: Long,
    val team_name: String){
    override fun toString(): String = team_name
}


class PersonTeamInfo(
    @Embedded
    val person: Person? = null,
    @Relation(parentColumn = "teamid", entityColumn = "teamid")
    val team: Team? = null
)


@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAll(): LiveData<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person): Long

    @Update
    fun update(person: Person)

    @Query("SELECT * FROM Person WHERE Person.uid = :userid")
    fun getPerson(userid: Int): Person

    @Query("SELECT * FROM person")
    fun getPersonTeamInfo(): LiveData<List<PersonTeamInfo>>
}


@Dao
interface TeamDao {
    @Query("SELECT * FROM team")
    fun getAll(): List<Team>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team: Team): Long

    @Update
    fun update(team: Team)
}


@Database(entities = [(Person::class), (Team::class)], version = 1)
abstract class PersonDatabase: RoomDatabase(){
    abstract fun personDao(): PersonDao
    abstract fun teamDao(): TeamDao

    companion object{
        private var sInstance: PersonDatabase? = null
        @Synchronized
        fun get(context: Context): PersonDatabase{
            if(sInstance == null){
                sInstance=
                    Room.databaseBuilder(context.applicationContext, PersonDatabase::class.java, "personDB").build()
            }
            return sInstance!!
        }



    }
}