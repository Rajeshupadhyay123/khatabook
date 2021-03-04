package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.ProfileDao
import com.example.noteapp.database.ProfileDatabase
import com.example.noteapp.model.Person
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application):AndroidViewModel(application) {
    var dao: ProfileDao
     var profileTable:LiveData<Person>
    init {
        dao=ProfileDatabase.getInstance(application).myDao()
        profileTable=dao.readPerson(1)
    }

    private fun insertPerson(person:Person, count:Int){
        if(count==0){
            viewModelScope.launch {
                insertPer(person)
            }
        }
    }
    private suspend fun insertPer(person: Person){
        dao.insertPerson(person)
    }
    fun updatePerson(person: Person){
        viewModelScope.launch {
            upadtePer(person)
        }
    }

    private suspend fun upadtePer(person: Person){
        dao.updatePerson(person)
    }

    fun insertCheck(person: Person){
        viewModelScope.launch {
            countCreate(person)
        }
    }

    private suspend fun countCreate(person: Person){
        val count=dao.insertCheck(person.person_id)
        insertPerson(person,count)
    }

    fun nameUpdate(name:String,person_id:Int){
        viewModelScope.launch {
            dao.nameUpdate(name,person_id)
        }
    }

    fun emailUpdate(email:String,person_id: Int){
        viewModelScope.launch {
            dao.emailUpdate(email, person_id)
        }
    }
    fun phoneUpdate(phone:String,person_id: Int){
        viewModelScope.launch {
            dao.phoneUpdate(phone, person_id)
        }
    }

    fun addressUpdate(address:String,person_id: Int){
        viewModelScope.launch {
            dao.addressUpdate(address, person_id)
        }
    }
}