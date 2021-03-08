package com.example.shop_list_retrofit.data.models

import com.google.gson.annotations.SerializedName

data class ItemRequest (

    @field:SerializedName("itemName")
    val itemName: String,

    @field:SerializedName("dateCreated")
    val dateCreated: String,

    @field:SerializedName("quantity")
    val quantity: Int,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("note")
    val note: String
        )