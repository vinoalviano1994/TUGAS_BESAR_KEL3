package com.alviano.ugd_kel3.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alviano.ugd_kel3.entity.Customer

@Database(
    entities = [Customer::class],
    version = 2
)
abstract class CustomerDB: RoomDatabase() {
    abstract fun customerDao(): CustomerDao

    companion object{
        @Volatile private var instance: CustomerDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance?:
        synchronized(LOCK){
            instance?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CustomerDB::class.java,
                "atmaSalon.db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}