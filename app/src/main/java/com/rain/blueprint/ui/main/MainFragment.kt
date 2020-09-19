package com.rain.blueprint.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rain.blueprint.R
import com.rain.blueprint.database.OrderDatabase
import com.rain.blueprint.databinding.MainFragmentBinding

class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = OrderDatabase.getInstance(application).orderDao

        val viewModel: MainViewModel by lazy {
            ViewModelProvider(
                this,
                MainViewModel.Factory(dataSource)
            ).get(MainViewModel::class.java)
        }

        val adapter = MainMenuAdapter(MenuToppingListener {
            viewModel.onMenuClicked(it)
        })

        binding.menuList.adapter = adapter

        viewModel.menus.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(it)
                )
                viewModel.doneNavigating()
            }
        })

        binding.menuList.layoutManager = LinearLayoutManager(activity)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

}