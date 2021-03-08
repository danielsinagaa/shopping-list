package com.example.shop_list_retrofit.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop_list_retrofit.R
import com.example.shop_list_retrofit.data.models.ItemEntity
import com.example.shop_list_retrofit.data.models.ItemResponse
import com.example.shop_list_retrofit.data.models.SingleResponse
import com.example.shop_list_retrofit.data.repositories.ItemRepositoryImpl
import com.example.shop_list_retrofit.databinding.FragmentListBinding
import com.example.shop_list_retrofit.presentation.ui.LoadingDialog
import com.example.shop_list_retrofit.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var itemListViewAdapter: ItemListViewAdapter
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ListItemViewModel
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewmodel()
        itemListViewAdapter = ItemListViewAdapter(viewModel)
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingDialog = LoadingDialog.build(requireContext())
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            binding.itemRecyclerView.apply {
                adapter = itemListViewAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewModel.getAllItemListData()
    }

    private fun initViewmodel() {
        viewModel = ViewModelProvider(this).get(ListItemViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.itemListLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val response = it.data as ItemResponse
                    val listItem = response.list.itemsEntity
                    itemListViewAdapter.setItemList(listItem)
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.itemDeleteLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    val response = it.data as SingleResponse
                    val deleted = response.itemEntity
                    Toast.makeText(requireContext(), "DELETED ITEM with id : ${deleted.id} and name : ${deleted.itemName}", Toast.LENGTH_SHORT).show()
                    viewModel.getAllItemListData()
                    loadingDialog.hide()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.itemFindByIdLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    val response = it.data as SingleResponse
                    val findItemById = response.itemEntity
                    val bundle = bundleOf("item_update" to findItemById)
                    Navigation.findNavController(requireView()).navigate(R.id.action_listFragment_to_formFragment, bundle)
                    loadingDialog.hide()

                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}