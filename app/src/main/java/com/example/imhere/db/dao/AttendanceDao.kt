package com.example.imhere.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.imhere.db.entity.AttendanceEntity

@Dao
interface AttendanceDao {
    @Insert
    suspend fun addAttendance(a : AttendanceEntity)

    @Delete
    suspend fun delAttendance(a : AttendanceEntity)

    @Query("SELECT * FROM attendance")
    suspend fun getAll() : List<AttendanceEntity>

    @Query("SELECT * FROM attendance WHERE id = :iid")
    suspend fun getOne(iid : Int) : AttendanceEntity?

    @Query("SELECT * FROM attendance WHERE meeting_id = :meetingId")
    suspend fun getAttendanceForMeeting(meetingId: Int): List<AttendanceEntity>?
}