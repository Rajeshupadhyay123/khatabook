package com.example.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.database.TransactionDao
import java.lang.IllegalArgumentException

class DetailFragmentFactory(val phoneNumber:String,val dataSource:TransactionDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailFragmentViewModel::class.java)){
            return DetailFragmentViewModel(phoneNumber,dataSource) as T
        }
        throw IllegalArgumentException("Unknown model class")
    }
}