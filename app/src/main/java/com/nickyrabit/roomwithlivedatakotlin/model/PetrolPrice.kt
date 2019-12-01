package com.nickyrabit.roomwithlivedatakotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**Created By Nickyrabit
 * nickyrabit@gmail.com
 * nicky@nickylegnard.com*/
@Entity
data class PetrolPrice (
    //Entities are tables and columns
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "fuel_price") val fuel_price:Double




)