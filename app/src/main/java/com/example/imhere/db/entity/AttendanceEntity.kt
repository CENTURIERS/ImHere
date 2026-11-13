package com.example.imhere.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName="attendance",
    foreignKeys = [
        ForeignKey(
            entity = MeetingEntity::class,
            parentColumns = ["id"],
            childColumns = ["meeting_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("meeting_id")]
)
data class AttendanceEntity (
    @PrimaryKey(autoGenerate = true) val id :Int = 0,
    @ColumnInfo("meeting_id") val meetingId :Int,
    @ColumnInfo("student_first_name") val studentFirstName : String,
    @ColumnInfo("student_second_name") val studentSecondName :String,
    @ColumnInfo("student_index") val studentIndex :String,
    @ColumnInfo("student_email") val studentEmail :String,
    @ColumnInfo("scanned_at") val scannedAt :Long ,
    @ColumnInfo("is_biometric_verified") val isBiometricVerified :Boolean,
)