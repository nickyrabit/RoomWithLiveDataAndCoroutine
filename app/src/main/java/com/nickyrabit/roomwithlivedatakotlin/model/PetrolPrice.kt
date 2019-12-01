package com.nickyrabit.roomwithlivedatakotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PetrolPrice (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "fuel_price") val fuel_price:Double




)