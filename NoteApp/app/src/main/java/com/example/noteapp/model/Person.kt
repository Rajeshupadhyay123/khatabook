package com.example.noteapp.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "my_table")
@Parcelize
data class Person(

    @PrimaryKey(autoGenerate = false)
    var person_id:Int=0,

    @ColumnInfo(name = "name")
    var name: String="",

    @ColumnInfo(name = "email")
    var email: String="",

    @ColumnInfo(name="address")
    var address:String="",

    @ColumnInfo(name = "phone")
    var phone:String="",

    @ColumnInfo(name = "profilePhoto")
    var profilePhoto: Bitmap?
):Parcelable