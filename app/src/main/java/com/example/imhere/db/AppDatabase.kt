package com.example.imhere.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imhere.db.dao.AttendanceDao
import com.example.imhere.db.dao.MeetingDao
import com.example.imhere.db.dao.UserDao
import com.example.imhere.db.entity.AttendanceEntity
import com.example.imhere.db.entity.MeetingEntity
import com.example.imhere.db.entity.UserEntity

@Database(entities = [UserEntity::class, MeetingEntity::class, AttendanceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun meetingDao(): MeetingDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "imhere_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}