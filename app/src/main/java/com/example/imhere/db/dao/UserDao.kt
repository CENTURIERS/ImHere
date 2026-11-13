package com.example.imhere.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.imhere.db.entity.UserEntity

@Dao
interface UserDao {
    @Query("Select id, first_name, second_name, `index`, email FROM user WHERE id = (:userId)")
    suspend fun getUser(userId : Int): UserEntity?

    @Insert
    suspend fun addUser(user: UserEntity)

    @Delete
    suspend fun delUser(user: UserEntity)

    @Update
    suspend fun updUser(user: UserEntity)
}