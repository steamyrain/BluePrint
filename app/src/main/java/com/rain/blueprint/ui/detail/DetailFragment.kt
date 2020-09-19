package com.rain.blueprint.ui.detail

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
import com.rain.blueprint.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: DetailFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.detail_fragment,
            container,
            false
        )

        val application = requireActivity().application

        val dataSource = OrderDatabase.getInstance(application).orderDao

        val arguments = DetailFragmentArgs.fromBundle(requireArguments())

        val viewModel: DetailViewModel by lazy {
            ViewModelProvider(
                this,
                DetailViewModel.Factory(dataSource, arguments.menuKey)
            ).get(DetailViewModel::class.java)
        }

        val adapter = DetailAdapter()

        viewModel.toppings.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToMain.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == true) {
                    this.findNavController().navigate(
                        DetailFragmentDirections.actionDetailFragmentToMainFragment()
                    )
                    viewModel.doneNavigating()
                }
            }
        })

        binding.toppingList.adapter = adapter

        binding.toppingList.layoutManager = LinearLayoutManager(activity)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }
}