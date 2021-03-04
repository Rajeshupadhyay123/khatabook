package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.UserDatabase
import com.example.noteapp.model.Customer
import com.example.noteapp.model.CustomerDetail
import com.example.noteapp.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val customerTable: LiveData<List<Customer>>

    //lateinit var customerDetailTable:LiveData<List<CustomerDetail>>
    val transactionRepository: TransactionRepository
    var insertFlag = MutableLiveData<Int>(0)

    init {
        val dao = UserDatabase.getInstance(application).transactionDao
        transactionRepository = TransactionRepository(dao)
        customerTable = transactionRepository.getCustomerTable()
    }

    fun searchCustomer(query: String?) = transactionRepository.searchCustomer(query)

    fun insertCustomerDetail(detail: CustomerDetail) {
        viewModelScope.launch {
            transactionRepository.insertCustomerDetail(detail)
        }
    }

    fun insertCustomer(customer: Customer, count: Int) {
        if (count == 0) {
            viewModelScope.launch {
                transactionRepository.insertCustomer(customer)
            }
        } else {
            insertFlag.value = count
        }

    }

    fun insertCheck(customer: Customer) {
        viewModelScope.launch {
            insertOperation(customer)
        }
    }

    private suspend fun insertOperation(customer: Customer) {
        val count = transactionRepository.insertCount(customer.phoneNumber)
        insertCustomer(customer, count)
    }


    fun deleteCutomer(customer: Customer) {
        viewModelScope.launch {
            transactionRepository.deleteCustomer(customer)
        }
    }

    fun deleteDetail(customer: Customer) {
        viewModelScope.launch {
            transactionRepository.deleteDetail(customer.phoneNumber)
        }
    }

}