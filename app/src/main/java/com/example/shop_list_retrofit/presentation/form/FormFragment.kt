package com.example.shop_list_retrofit.presentation.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.shop_list_retrofit.R
import com.example.shop_list_retrofit.data.models.ItemEntity
import com.example.shop_list_retrofit.data.models.ItemRequest
import com.example.shop_list_retrofit.data.models.ItemResponse
import com.example.shop_list_retrofit.data.models.SingleResponse
import com.example.shop_list_retrofit.data.repositories.ItemRepositoryImpl
import com.example.shop_list_retrofit.databinding.FragmentFormBinding
import com.example.shop_list_retrofit.presentation.list.ListItemViewModel
import com.example.shop_list_retrofit.presentation.ui.LoadingDialog
import com.example.shop_list_retrofit.utils.ResourceStatus
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment() {

    private var itemUpdate: ItemEntity? = null
    private lateinit var binding: FragmentFormBinding

    private lateinit var listViewModel: ListItemViewModel
    private lateinit var formViewModel: FormViewModel

    private lateinit var loadingDialog: AlertDialog
    private var formatDate = SimpleDateFormat("YYYY/MM/DD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            itemUpdate = this.getParcelable("item_update")
        }
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog.build(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.apply {
            dateInput.setInputType(InputType.TYPE_NULL)
            dateInput.setOnClickListener(View.OnClickListener {
                val datePickerDialog = activity?.let { it1 ->
                    DatePickerDialog(
                            it1, DatePickerDialog.OnDateSetListener
                    { view, year, monthOfYear, dayOfMonth ->
                        if (monthOfYear.toString().length == 1 && dayOfMonth.toString().length == 1){
                            dateInput.setText(
                                    "$year/0$monthOfYear/0$dayOfMonth",
                                    TextView.BufferType.EDITABLE)
                        } else if (monthOfYear.toString().length == 1 && dayOfMonth.toString().length != 1){
                            dateInput.setText(
                                    "$year/0$monthOfYear/$dayOfMonth",
                                    TextView.BufferType.EDITABLE)
                        }else if (monthOfYear.toString().length != 1 && dayOfMonth.toString().length == 1){
                            dateInput.setText(
                                    "$year/$monthOfYear/0$dayOfMonth",
                                    TextView.BufferType.EDITABLE)
                        } else {
                            dateInput.setText(
                                    "$year/$monthOfYear/$dayOfMonth",
                                    TextView.BufferType.EDITABLE)
                        }
                    }, year, month, day
                    )
                }
                datePickerDialog?.show()
            })
        }

        if (itemUpdate != null) {
            binding.apply {
                formTitle.setText("EDIT ")
                inputBtn.text = "UPDATE"
                itemUpdate?.apply {
                    val dateShopSubString = dateCreated.substring(0, 10)
//                            val sdf = SimpleDateFormat("yyyy/mm/dd")
//                            calendar.time = sdf.parse(dateShopSubString)
//                            val stringDate = formatDate.format(calendar.time)
                    dateInput.setText(dateShopSubString)
                    quantityInput.setText(quantity.toString())
                    itemNameInput.setText(itemName)
                    noteInput.setText(note)
                    priceInput.setText(price.toString())

                    inputBtn.setOnClickListener {
                        val itemId = itemUpdate?.id!!
                        val updatedItem = ItemRequest(
                                dateCreated = dateInput.text.toString(),
                                itemName = itemNameInput.text.toString(),
                                quantity = quantityInput.text.toString().toInt(),
                                note = noteInput.text.toString(),
                                price =  priceInput.text.toString().toInt()
                        )
                        formViewModel.updateItemById(itemId, updatedItem)
                    }
                }
            }
        } else {
            binding.apply {
                inputBtn.setOnClickListener {
                    val inputItemNameString = itemNameInput.text.toString()
                    val inputshoppingDateString = dateInput.text.toString()
                    val inputQuantityString = quantityInput.text.toString()
                    val inputNotesString = noteInput.text.toString()
                    val inputPriceString = priceInput.text.toString()
                    formViewModel.inputValidation(
                            inputItemNameString, inputshoppingDateString, inputQuantityString, inputNotesString, inputPriceString
                    )
                }
            }
        }

    }

    private fun clearEditText() {
        binding.apply {
            dateInput.text?.clear()
            itemNameInput.text?.clear()
            quantityInput.text?.clear()
            priceInput.text?.clear()
            noteInput.text?.clear()
        }
    }

    private fun initViewModel() {
        listViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoryImpl()
                return ListItemViewModel(repo) as T
            }

        }).get(ListItemViewModel::class.java)
        formViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoryImpl()
                return FormViewModel(repo) as T
            }

        }).get(FormViewModel::class.java)
    }

    private fun subscribe() {
        formViewModel.inputValidation.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                    binding.inputBtn.isEnabled = false
                }
                ResourceStatus.SUCCESS -> {
                    Log.i("ini add item fragment", "RESOURCE STATE SUCCESS")
                    loadingDialog.hide()
                    binding.apply {
                        val inputItemNameString = itemNameInput.text.toString()
                        val inputshoppingDateString = dateInput.text.toString()
                        val inputQuantityString = quantityInput.text.toString()
                        val inputNotesString = noteInput.text.toString()
                        val priceString = priceInput.text.toString().toInt()
                        val addItemRequest = ItemRequest(
                                itemName = inputItemNameString,
                                dateCreated = inputshoppingDateString,
                                quantity = inputQuantityString.toInt(),
                                note = inputNotesString,
                                price = priceString)
                        inputBtn.isEnabled = true
                        formViewModel.addItemShopping(addItemRequest)
                    }
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.inputBtn.isEnabled = true
                }
            }
        })

        formViewModel.addItemShoppingLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.SUCCESS -> {
                    val response = it.data as SingleResponse
                    val itemName = response.itemEntity.itemName
                    clearEditText()
                    Toast.makeText(requireContext(), "Add item with $itemName", Toast.LENGTH_LONG).show()
                }
                ResourceStatus.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.LOADING -> {
                    Toast.makeText(requireContext(), "Waiting for Response", Toast.LENGTH_SHORT).show()
                }
            }
        })

        formViewModel.updateItemShoppingLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.SUCCESS -> {
                    val response = it.data as SingleResponse
                    val itemName = response.itemEntity.itemName
                    Toast.makeText(requireContext(), "Update item with $itemName", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigate(R.id.action_formFragment_to_listFragment)
                }
                ResourceStatus.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.LOADING -> {
                    Toast.makeText(requireContext(), "Waiting for Response", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        Log.i("INI ADD ITEM FRAGMENT", "ON PAUSE")
        binding.apply {
            formViewModel.inputValidation(
                    itemNameInput.text.toString(),
                    dateInput.text.toString(),
                    quantityInput.text.toString(),
                    noteInput.text.toString(),
                    priceInput.toString()
            )
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = FormFragment()
    }
}