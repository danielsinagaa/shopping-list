package com.example.shop_list_retrofit.utils

import com.example.shop_list_retrofit.data.models.ItemEntity

interface ItemClickListener {

    fun onDelete(item: ItemEntity)
    fun onUpdate(item: ItemEntity)
}