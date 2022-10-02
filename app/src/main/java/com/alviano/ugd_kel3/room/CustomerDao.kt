package com.alviano.ugd_kel3.room

import androidx.room.*
import com.alviano.ugd_kel3.entity.Customer


@Dao
interface CustomerDao {
    @Insert
    suspend fun addCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("SELECT * FROM customer")
    suspend fun getCustomers(): List<Customer>

    @Query("SELECT * FROM customer WHERE id=:id_customer")
    suspend fun getCustomer(id_customer: Int): List<Customer>
}