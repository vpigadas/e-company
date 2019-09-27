package com.vpigadas.e_company.model

import androidx.room.*

@Dao
interface UserDao {

    @Query("Select * from users")
    fun getAll(): List<LocalUser>

    @Query("Select * from users where id = :userId limit 1")
    fun get(userId: Int): LocalUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: LocalUser)

    @Delete
    fun delete(movie: LocalUser)
}