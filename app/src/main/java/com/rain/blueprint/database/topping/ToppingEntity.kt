package com.rain.blueprint.database.topping

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "topping_table")
data class ToppingEntity constructor(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "topping_name")
    val name: String,

    @ColumnInfo(name = "date_added")
    val cteatedDate: Date,

    @ColumnInfo(name = "status")
    val status: String
)
