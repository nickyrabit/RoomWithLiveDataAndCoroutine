package com.nickyrabit.roomwithlivedatakotlin.db
/**Created By Nickyrabit
 * nickyrabit@gmail.com
 * nicky@nickylegnard.com*/

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nickyrabit.roomwithlivedatakotlin.dao_interfaces.PetrolPriceDao
import com.nickyrabit.roomwithlivedatakotlin.model.PetrolPrice
/**
 *Look at the @Database annotation, we have defined
 * the table we made earlier by calling it entities
 * {PetrolPrice.class} you can name as any tables as you like there
 *
 *
 * VERSION is the version of the table if you alter it you need to add a new version.
 *
 *exportSchema is used when you want to keep track of the  database versions. Turn it off in Production yoooooooo!!!
 *
 *
 *
 */

@Database(entities = [PetrolPrice::class],version = 1)
abstract class FuelPriceDatabase : RoomDatabase(){
abstract fun petrolPriceDao():PetrolPriceDao;
//Inside the code, we have this line inside a constructor which defines the database "Price Database" by using DatabaseBuilder method
    //DatabaseBuilder Creates a RoomDatabase.Builder for a persistent database. Once a database is built, you should keep a reference to it and re-use it.

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