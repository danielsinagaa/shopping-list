package com.example.shop_list_retrofit.data.models

import com.google.gson.annotations.SerializedName

data class ListItem (
    @field:SerializedName("list")
    val itemsEntity: List<ItemEntity>
    )