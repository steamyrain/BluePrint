package com.rain.blueprint.database.combo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.rain.blueprint.database.mainmenu.MainMenuEntity
import com.rain.blueprint.database.topping.ToppingEntity

@Entity(
    tableName = "combo_table",
    primaryKeys = ["menu_id", "topping_id"],
    foreignKeys = [
        ForeignKey(
            entity = MainMenuEntity::class,
            parentColumns = ["id"],
            childColumns = ["menu_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ToppingEntity::class,
            parentColumns = ["id"],
            childColumns = ["topping_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ComboEntity constructor(
    @ColumnInfo(name = "menu_id")
    val menuId: Int,

    @ColumnInfo(name = "topping_id")
    val toppingId: Int
)