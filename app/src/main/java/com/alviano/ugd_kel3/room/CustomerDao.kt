package com.alviano.ugd_kel3.room

import androidx.room.*


@Dao
interface CustomerDao {
    @Insert
    suspend fun addCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("SELECT * FROM customer")
    suspend fun getCustomer(): List<Customer>

    @Query("SELECT * FROM customer WHERE id=:customer_id")
    suspend fun getCustomer(customer_id: Int) : List<Customer>
}