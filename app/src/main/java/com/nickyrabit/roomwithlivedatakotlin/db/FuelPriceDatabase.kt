package com.nickyrabit.roomwithlivedatakotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nickyrabit.roomwithlivedatakotlin.dao_interfaces.PetrolPriceDao
import com.nickyrabit.roomwithlivedatakotlin.model.PetrolPrice

@Database(entities = [PetrolPrice::class],version = 1)
abstract class FuelPriceDatabase : RoomDatabase(){
abstract fun petrolPriceDao():PetrolPriceDao;

    companion object {
        @Volatile private var instance: FuelPriceDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: getAppDatabase(context).also { instance = it}
        }

        fun getAppDatabase(context: Context) = Room.databaseBuilder(context,
            FuelPriceDatabase::class.java, "fuel-database")
            .build()
    }
}