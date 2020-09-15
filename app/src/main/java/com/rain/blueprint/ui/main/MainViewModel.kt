package com.rain.blueprint.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rain.blueprint.database.OrderDao

class MainViewModel(dataSource: OrderDao) : ViewModel() {

    val menus = dataSource.getMenus()

    class Factory(private val dataSource: OrderDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

