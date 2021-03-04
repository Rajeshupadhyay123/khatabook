package com.example.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.noteapp.model.Customer
import com.example.noteapp.model.CustomerDetail
import com.example.noteapp.model.User

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertCustomer(customer: Customer)

    @Insert
    suspend fun insertDetail(customerDetail: CustomerDetail)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("select sum(giveAmount) from customer_detail where phoneNumber=:phoneNumber")
    suspend fun getGaveAmount(phoneNumber:String?):Double

    @Query("select sum(getAmount) from customer_detail where phoneNumber=:phoneNumber")
    suspend fun getGetAmount(phoneNumber: String?):Double

    @Query("select *from customer_detail where phoneNumber=:phoneNumber order by customer_id desc")
    fun getDetailTable(phoneNumber: String?):LiveData<List<CustomerDetail>>

    @Query("select *from customer")
    fun getCustomerTable():LiveData<List<Customer>>

    @Query("select *from customer where phoneNumber like :query or customer_name like:query")
    fun searchCustomer(query: String?):LiveData<List<Customer>>

    @Query("delete from customer_detail where phoneNumber=:phoneNumber")
    suspend fun deleteDetail(phoneNumber: String?)

    @Query("select phoneNumber from customer where phoneNumber=:phoneNumber")
    suspend fun insertCheck(phoneNumber: String?):String

    @Query("select count(phoneNumber) from customer where phoneNumber=:phoneNumber")
    suspend fun insertCount(phoneNumber: String?):Int

    @Query("update customer set currentAmount=:currentAmount where phoneNumber=:phoneNumber")
    suspend fun updateCustomer(currentAmount:Double?,phoneNumber: String?)
}