package com.example.shop_list_retrofit.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop_list_retrofit.data.api.ItemRepository
import com.example.shop_list_retrofit.data.models.ItemEntity
import com.example.shop_list_retrofit.utils.ItemClickListener
import com.example.shop_list_retrofit.utils.ResourceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListItemViewModel(private val itemRepository: ItemRepository) : ViewModel(),
    ItemClickListener {

    private var _itemListLiveData = MutableLiveData<ResourceState>()
    val itemListLiveData: LiveData<ResourceState>
        get() {
            return _itemListLiveData
        }

    private var _itemDeleteLiveData = MutableLiveData<ResourceState>()
    val itemDeleteLiveData: LiveData<ResourceState>
        get() {
            return _itemDeleteLiveData
        }

    private var _itemFindByIdLiveData = MutableLiveData<ResourceState>()
    val itemFindByIdLiveData: LiveData<ResourceState>
        get() {
            return  _itemFindByIdLiveData
        }

    fun getAllItemListData() {
        CoroutineScope(Dispatchers.Main).launch {
            _itemListLiveData.postValue(ResourceState.loading())
            val response = itemRepository.getAllShoppingList()
            val responseBody = response.body()!!
            if (response.isSuccessful) {
                _itemListLiveData.postValue(ResourceState.success(responseBody))
            } else {
                _itemListLiveData.postValue(ResourceState.failure(responseBody.message.toString()))
            }
        }
    }

    override fun onDelete(item: ItemEntity) {
        CoroutineScope(Dispatchers.Main).launch {
            _itemDeleteLiveData.postValue(ResourceState.loading())
            val response = itemRepository.deleteItemShopping(item.id)
            val responseBody = response.body()
            if (response.isSuccessful) {
                _itemDeleteLiveData.postValue(responseBody?.let {
                    ResourceState.success(it)
                })
            } else {
                _itemDeleteLiveData.postValue(ResourceState.failure(response.message()))
            }
        }
    }

    override fun onUpdate(item: ItemEntity) {
        CoroutineScope(Dispatchers.Main).launch {
            _itemFindByIdLiveData.postValue(ResourceState.loading())
            val response = itemRepository.findItemById(item.id)
            val responseBody = response.body()!!
            if(response.isSuccessful) {
                _itemFindByIdLiveData.postValue(ResourceState.success(responseBody))
            } else {
                _itemFindByIdLiveData.postValue(ResourceState.failure("${responseBody.message}"))
            }
        }
    }
}