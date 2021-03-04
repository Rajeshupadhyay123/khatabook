package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.UserDatabase
import com.example.noteapp.model.User
import com.example.noteapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {

    val userTable:LiveData<List<User>>
    val userRepository:UserRepository

    init {
        val userDao=UserDatabase.getInstance(application).userDao
        userRepository= UserRepository(userDao)
        userTable=userRepository.getAllTable()
    }

    fun insert(user:User){
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }

    fun update(user:User){
        viewModelScope.launch {
            userRepository.update(user)
        }
    }
    fun delete(user: User){
        viewModelScope.launch {
            userRepository.delete(user)
        }
    }

    fun searchNote(query:String?)=userRepository.searchNote(query)
}