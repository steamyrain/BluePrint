package com.rain.blueprint.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rain.blueprint.database.OrderDao
import com.rain.blueprint.databinding.ListItemMainMenuBinding

class MainMenuAdapter :
    ListAdapter<OrderDao.MainMenu, RecyclerView.ViewHolder>(MainMenuDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                Log.d("BindViewHolder", "isViewHolder")
                val menuItem = getItem(position) as OrderDao.MainMenu
                holder.bind(menuItem)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemMainMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMainMenuBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: OrderDao.MainMenu) {
            binding.menu = item
            binding.executePendingBindings()
        }
    }
}

class MainMenuDiffCallback : DiffUtil.ItemCallback<OrderDao.MainMenu>() {
    override fun areItemsTheSame(oldItem: OrderDao.MainMenu, newItem: OrderDao.MainMenu): Boolean {
        return oldItem.menuId == newItem.menuId
    }

    override fun areContentsTheSame(
        oldItem: OrderDao.MainMenu,
        newItem: OrderDao.MainMenu
    ): Boolean {
        return oldItem == newItem
    }

}