package com.example.imhere.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.imhere.db.entity.MeetingEntity

@Dao
interface MeetingDao {
    @Query("SELECT * FROM meeting")
    suspend fun getAll() : List<MeetingEntity>

    @Query("SELECT * FROM meeting WHERE id = :iid")
    suspend fun getOne(iid : Int) : MeetingEntity?

    @Insert
    suspend fun addMeeting(meeting: MeetingEntity)

    @Delete
    suspend fun delMeeting(meeting: MeetingEntity)

    @Update
    suspend fun updateOne(met : MeetingEntity)
}