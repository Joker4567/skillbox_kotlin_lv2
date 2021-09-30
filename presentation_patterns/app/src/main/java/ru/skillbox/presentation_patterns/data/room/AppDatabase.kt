package ru.skillbox.presentation_patterns.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.skillbox.presentation_patterns.data.room.AppDatabase.Companion.DATABASE_VERSION
import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity

@Database(
        entities = [WeatherEntity:: class],
        version = DATABASE_VERSION,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWeatherDao() : WeatherDao

    companion object {
        lateinit var instance: AppDatabase
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "appSkillBox_db"

        fun buildDataSource(context: Context): AppDatabase {
            val room = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            instance = room
            return room
        }
    }
}