package com.rain.blueprint.database.mainmenu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "main_menu_table")
data class MainMenuEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "menu_name")
    val name: String,

    @ColumnInfo(name = "date_added")
    val cteatedDate: Date,

    @ColumnInfo(name = "status")
    val status: String
)