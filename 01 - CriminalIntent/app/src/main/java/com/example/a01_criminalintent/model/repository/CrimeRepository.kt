package com.example.a01_criminalintent.model.repository

import android.content.Context
import androidx.room.Room
import com.example.a01_criminalintent.model.room.Crime
import com.example.a01_criminalintent.model.room.CrimeDatabase
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.util.UUID

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context){

    private val filesDir = context.applicationContext.filesDir

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()

    fun getCrimes(): Flow<List<Crime>> = crimeDao.getCrimes()

    suspend fun getCrime(id: UUID): Crime? = crimeDao.getCrime(id)

    suspend fun addCrime(crime: Crime) = crimeDao.addCrime(crime)

    suspend fun updateCrime(crime: Crime) = crimeDao.updateCrime(crime)

    fun getPhotoCrime(crime: Crime): File = File(filesDir, crime.photoFileName)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }

}