package com.vpigadas.e_company.model

import androidx.room.*

@Dao
interface CompanyDao {

    @Query("Select * from company")
    fun getAll(): List<LocalCompany>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: LocalCompany)

    @Delete
    fun delete(movie: LocalCompany)
}