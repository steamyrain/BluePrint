package com.rain.blueprint.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rain.blueprint.database.combo.ComboEntity
import com.rain.blueprint.database.mainmenu.MainMenuEntity
import com.rain.blueprint.database.topping.ToppingEntity
import com.rain.blueprint.utils.Converters

@Database(
    entities = [MainMenuEntity::class, ToppingEntity::class, ComboEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class OrderDatabase : RoomDatabase() {
    companion object {
        /***
         * Volatile - value from @Volatile never cached, read & write done from
         * and to the main memory. Change made by one thread to the shared data are
         * visible to the other threads.
         */
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getInstance(context: Context): OrderDatabase {
            /***
             * kolin.synchronized - lock OrderDatabase instance when getInstance called.
             * and executes all the codes inside the synchronized block.
             * That way there's only a single thread that can access OrderDatabase instance
             * when getInstance called.
             */
            kotlin.synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OrderDatabase::class.java,
                        "blueprint_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}