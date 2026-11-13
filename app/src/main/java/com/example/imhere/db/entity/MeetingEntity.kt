package com.example.imhere.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo

@Entity("meeting")
data class MeetingEntity (
    @PrimaryKey(autoGenerate = true) val id :Int = 0,
    @ColumnInfo("name") val name :String,
    @ColumnInfo("date") val date :Long,
    @ColumnInfo("start_hour") val startHour :Long,
    @ColumnInfo("end_hour") val endHour :Long,
    @ColumnInfo("leader_name") val leaderName :String,
    @ColumnInfo("type") val type :Boolean,
)