package com.rain.blueprint.database

import android.content.Context
import androidx.room.*
import com.rain.blueprint.database.combo.ComboEntity
import com.rain.blueprint.database.mainmenu.MainMenuEntity
import com.rain.blueprint.database.topping.ToppingEntity
import com.rain.blueprint.utils.Converters

@Dao
interface OrderDao {
    @Query("select m.menu_name as menuName, t.topping_name as toppingName from main_menu_table as m inner join combo_table as c on c.menu_id == m.id inner join topping_table as t on t.id == c.topping_id where t.id = 1 LIMIT 1")
    fun getCombos(): MenuTopping

    data class MenuTopping(val menuName: String?, val toppingName: String?)
}

@Database(
    entities = [MainMenuEntity::class, ToppingEntity::class, ComboEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class OrderDatabase : RoomDatabase() {

    abstract val orderDao: OrderDao

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
                        "sample.db"
                    )
                        .createFromAsset("databases/sample.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}