package com.alviano.ugd_kel3.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nama: String,
    val password: String,
    val tanggalLahir: String,
    val noTelp: String,
    val email: String
)
