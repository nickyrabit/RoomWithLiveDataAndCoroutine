package com.nickyrabit.roomwithlivedatakotlin.dao_interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nickyrabit.roomwithlivedatakotlin.model.PetrolPrice

@Dao
interface PetrolPriceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg c: PetrolPrice)


    @Query("SELECT * FROM 'PetrolPrice'")
    @OnConflictStrategy
    fun getAllPrices() : LiveData<List<PetrolPrice>>



}