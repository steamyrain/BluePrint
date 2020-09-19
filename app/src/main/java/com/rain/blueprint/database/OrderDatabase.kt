package com.rain.blueprint.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.rain.blueprint.database.combo.ComboEntity
import com.rain.blueprint.database.mainmenu.MainMenuEntity
import com.rain.blueprint.database.topping.ToppingEntity
import com.rain.blueprint.utils.Converters

@Dao
interface OrderDao {
    @Query("select id as menuId, menu_name as menuName from main_menu_table order by menu_name asc")
    fun getMenus(): LiveData<List<MainMenu>>

    @Query("select t.id as toppingId, t.topping_name as toppingName from main_menu_table as m inner join combo_table as c on c.menu_id == m.id inner join topping_table as t on t.id == c.topping_id where m.id = :menuId")
    fun getCombos(menuId: Int): LiveData<List<MenuTopping>>

    data class MenuTopping(val toppingId: Int?, val toppingName: String?)
    data class MainMenu(val menuId: Int?, val menuName: String?)
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
         * Volatile - value from @Volatile never cached (processor cache), read & write done from
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