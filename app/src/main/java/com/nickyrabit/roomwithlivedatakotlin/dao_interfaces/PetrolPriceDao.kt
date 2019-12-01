package com.nickyrabit.roomwithlivedatakotlin.dao_interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nickyrabit.roomwithlivedatakotlin.model.PetrolPrice
/**
 *Now there is something called Data Access Object or DAO.
 *  They are the main component in your Room Database.
 *  They process the queries and stuff
 *
 *
 * You declare them by using interfaces.
 * Now I am making a DAO called PetrolPriceDao.java
 * that will perform CRUD operations (Create, Read, Update, Delete)
 */
@Dao
interface PetrolPriceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg c: PetrolPrice)

    /*
    * I have set a LIVEDATA on
    *  PetrolPriceList which has the task of alerting all Observer object
    * when there is a change in a particular data in our case,
    * PetrolPrice.
    *
    *
    * A quick explanation on code below,
    *  @Query() is an annotation that will hold all SQLite queries
    *  because Room can perform only some few basic queries
    * without typing them.
    * */

    @Query("SELECT * FROM 'PetrolPrice'")
    @OnConflictStrategy
    fun getAllPrices() : LiveData<List<PetrolPrice>>



}