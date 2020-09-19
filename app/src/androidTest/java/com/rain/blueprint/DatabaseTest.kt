package com.rain.blueprint

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rain.blueprint.database.OrderDao
import com.rain.blueprint.database.OrderDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: OrderDao
    private lateinit var db: OrderDatabase
    private lateinit var data: OrderDao.MenuTopping

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = OrderDatabase.getInstance(context)
        dao = db.orderDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    /*
    @Test
    @Throws(Exception::class)
    fun getCombo() {
        val combos = dao.getCombos()
        assertEquals(combos.toppingName, "kacang")
    }*/

    @Test
    @Throws(Exception::class)
    fun getMenu() {
        val menus = dao.getMenus().blockingObserve()
        assertEquals(menus?.get(0)?.menuName, "Bubur Ayam")
    }
}