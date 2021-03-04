package com.example.noteapp.repository

import com.example.noteapp.database.UserDao
import com.example.noteapp.model.CustomerDetail
import com.example.noteapp.model.User

class UserRepository(val dao:UserDao) {

    fun getAllTable()=dao.getAllTable()

    suspend fun insert(user:User){
        dao.insert(user)
    }

    suspend fun update(user: User){
        dao.update(user)
    }

    suspend fun delete(user: User){
        dao.delete(user)
    }

    fun searchNote(query:String?)=dao.searchNote(query)
}