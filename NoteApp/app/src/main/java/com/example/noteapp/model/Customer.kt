package com.example.noteapp.model

import android.location.Address
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "customer")
data class Customer(

    @PrimaryKey(autoGenerate = false)
    var phoneNumber:String="",

    @ColumnInfo(name="customer_name")
    var name:String="",

    @ColumnInfo(name = "address")
    var address: String="",

    @ColumnInfo(name = "currentAmount")
    var currentAmount:Double=0.0
):Parcelable