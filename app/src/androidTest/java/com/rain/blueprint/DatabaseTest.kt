package com.rain.blueprint

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rain.blueprint.database.OrderDao
import com.rain.blueprint.database.OrderDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: OrderDao
    private lateinit var db: OrderDatabase
    private lateinit var data: OrderDao.MenuTopping

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = OrderDatabase.getInstance(context)
        dao = db.orderDao
        data = OrderDao.MenuTopping("Bubur Ayam", "kacang")
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getCombo() {
        val combos = dao.getCombos()
        assertEquals(combos.menuName, "Bubur Ayam")
        assertEquals(combos.toppingName, "kacang")
    }
}