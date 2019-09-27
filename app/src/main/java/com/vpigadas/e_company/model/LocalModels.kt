package com.vpigadas.e_company.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

interface LocalModel {
    fun equalContent(other: LocalModel): Boolean
}

data class LocalMore(
    val type: String
) : LocalModel {
    override fun equalContent(other: LocalModel): Boolean {
        return true
    }
}

@Entity(tableName = "company")
@Parcelize
data class LocalCompany(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "speciality") val speciality: String
) : LocalModel, Parcelable {
    override fun equalContent(other: LocalModel): Boolean {
        return false
    }
}

@Entity(tableName = "users")
@Parcelize
data class LocalUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "speciality") val speciality: String,
    @ColumnInfo(name = "company") val company: String
) : LocalModel, Parcelable {
    override fun equalContent(other: LocalModel): Boolean {
        return false
    }

    fun getName(): String = "$firstName $lastName"
}


