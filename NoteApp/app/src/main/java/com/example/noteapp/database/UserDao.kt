package com.example.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("select *from user")
    fun getAllTable():LiveData<List<User>>

    @Query("select *from user where title like :query or description like:query")
    fun searchNote(query: String?):LiveData<List<User>>
}