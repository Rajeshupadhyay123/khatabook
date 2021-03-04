package com.example.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.Customer
import com.example.noteapp.model.CustomerDetail
import com.example.noteapp.model.User

@Database(entities = [User::class,Customer::class,CustomerDetail::class],version = 9,exportSchema = false)
abstract class UserDatabase :RoomDatabase(){

    abstract val userDao:UserDao
    abstract val transactionDao:TransactionDao

    companion object{
        var INSTANCE:UserDatabase?=null

        fun getInstance(context: Context):UserDatabase{
            var instance= INSTANCE
            if(instance==null){
                instance=Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE=instance
            }
            return instance
        }
    }
}