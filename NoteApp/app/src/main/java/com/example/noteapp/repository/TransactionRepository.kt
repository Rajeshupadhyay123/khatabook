package com.example.noteapp.repository

import com.example.noteapp.database.TransactionDao
import com.example.noteapp.database.UserDao
import com.example.noteapp.model.Customer
import com.example.noteapp.model.CustomerDetail

class TransactionRepository(val dao:TransactionDao) {

    fun getCustomerTable()=dao.getCustomerTable()


    fun searchCustomer(query:String?)=dao.searchCustomer(query)

    suspend fun insertCustomer(customer: Customer){
        dao.insertCustomer(customer)
    }

    suspend fun insertCustomerDetail(detail: CustomerDetail){
        dao.insertDetail(detail)
    }

    suspend fun deleteCustomer(customer: Customer){
        dao.deleteCustomer(customer)
    }

    suspend fun deleteDetail(phoneNumber:String){
        dao.deleteDetail(phoneNumber)
    }

    suspend fun insertCount(phoneNumber: String):Int{
        return dao.insertCount(phoneNumber)
    }

}