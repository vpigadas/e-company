package com.vpigadas.e_company.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vpigadas.e_company.model.CompanyDao
import com.vpigadas.e_company.model.LocalCompany
import com.vpigadas.e_company.model.LocalUser
import com.vpigadas.e_company.model.UserDao

@Database(
    entities = arrayOf(LocalCompany::class, LocalUser::class),
    version = 2
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getCompanyDao(): CompanyDao
    abstract fun getUserDao(): UserDao
}

object AppDatabase {
    private const val dbName = "movie_app_database"

    fun getInstance(context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            dbName
        ).fallbackToDestructiveMigration().build()
}