package com.example.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp.model.Person

@Dao
interface ProfileDao {

    @Query("SELECT * FROM my_table where person_id=:person_id")
    fun readPerson(person_id:Int): LiveData<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Update
    suspend fun updatePerson(person: Person)

    @Query("select count(person_id) from my_table where person_id=:person_id")
    suspend fun insertCheck(person_id: Int?):Int

    @Query("update my_table set name=:name where person_id=:person_id")
    suspend fun nameUpdate(name:String?,person_id: Int?)

    @Query("update my_table set email=:email where person_id=:person_id")
    suspend fun emailUpdate(email:String?,person_id: Int?)

    @Query("update my_table set phone=:phone where person_id=:person_id")
    suspend fun phoneUpdate(phone:String?,person_id: Int?)

    @Query("update my_table set address=:address where person_id=:person_id")
    suspend fun addressUpdate(address:String?,person_id: Int?)
}