package com.example.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.TransactionDao
import com.example.noteapp.model.CustomerDetail
import kotlinx.coroutines.launch

class DetailFragmentViewModel(val phoneNumber:String,val database:TransactionDao):ViewModel() {

    var detailTable:LiveData<List<CustomerDetail>>


    var currentAmount= MutableLiveData<Double>(0.0)

    init {
        detailTable=database.getDetailTable(phoneNumber)
    }

    fun startCalculateCurrentAmount(phoneNumber: String){
        viewModelScope.launch {
            getCurrentAmount(phoneNumber)
        }
    }

    private suspend fun getCurrentAmount(phoneNumber: String){
        val gave:Double=database.getGaveAmount(phoneNumber)
        val get:Double=database.getGetAmount(phoneNumber)
        val data=gave-get
        database.updateCustomer(data,phoneNumber)
        currentAmount.value=data
    }
    /*fun updateDetail(currentAmount:String,phoneNumber: String){
        viewModelScope.launch {
            updateDetailSuspend(currentAmount,phoneNumber)
        }
    }*/

    /*suspend fun updateDetailSuspend(currentAmount: String,phoneNumber: String){
        database.updateDetail(currentAmount,phoneNumber)
    }*/
}