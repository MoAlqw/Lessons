package com.example.a01_criminalintent.model.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Crime::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(1, 2)
    ]
)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase: RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}