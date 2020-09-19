package com.rain.blueprint.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rain.blueprint.database.OrderDao

class DetailViewModel(dataSource: OrderDao, menuId: Int) : ViewModel() {

    val toppings = dataSource.getCombos(menuId)

    private val _navigateToMain = MutableLiveData<Boolean?>()

    val navigateToMain: LiveData<Boolean?>
        get() = _navigateToMain

    fun doneNavigating() {
        _navigateToMain.value = null
    }

    class Factory(private val dataSource: OrderDao, private val menuId: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(dataSource, menuId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}