package com.rain.blueprint.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rain.blueprint.database.OrderDao
import com.rain.blueprint.databinding.ListItemToppingBinding

class DetailAdapter :
    ListAdapter<OrderDao.MenuTopping, RecyclerView.ViewHolder>(ToppingDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val menuItem = getItem(position) as OrderDao.MenuTopping
        holder.bind(menuItem)
    }

    class ViewHolder private constructor(val binding: ListItemToppingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemToppingBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: OrderDao.MenuTopping) {
            binding.topping = item
            binding.executePendingBindings()
        }
    }
}

class ToppingDiffCallback : DiffUtil.ItemCallback<OrderDao.MenuTopping>() {
    override fun areItemsTheSame(
        oldItem: OrderDao.MenuTopping,
        newItem: OrderDao.MenuTopping
    ): Boolean {
        return oldItem.toppingId == newItem.toppingId
    }

    override fun areContentsTheSame(
        oldItem: OrderDao.MenuTopping,
        newItem: OrderDao.MenuTopping
    ): Boolean {
        return oldItem == newItem
    }

}
