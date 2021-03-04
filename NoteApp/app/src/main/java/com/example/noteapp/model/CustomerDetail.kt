package com.example.noteapp.model

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "customer_detail")
data class CustomerDetail(

    @PrimaryKey(autoGenerate = true)
    val customer_id:Long=0L,

    @ColumnInfo(name = "giveAmount")
    var gaveAmount:Double=0.0,

    @ColumnInfo(name = "getAmount")
    var getAmount:Double=0.0,

    @ColumnInfo(name = "time")
    var time:String="",

    @ColumnInfo(name = "phoneNumber")
    var phoneNumber:String=""

):Parcelable