package com.example.imhere.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id :Int = 0,
    @ColumnInfo(name = "first_name") val firstName : String,
    @ColumnInfo(name = "second_name") val secondName : String,
    @ColumnInfo(name = "index") val index : Int,
    @ColumnInfo(name = "email") val email : String,
)