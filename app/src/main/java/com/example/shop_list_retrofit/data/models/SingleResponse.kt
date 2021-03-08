package com.example.shop_list_retrofit.data.models

import com.google.gson.annotations.SerializedName

class SingleResponse(
        @field:SerializedName("code")
        val code: Int,

        @field:SerializedName("data")
        val itemEntity: ItemEntity,

        @field:SerializedName("message")
        val message: String,

        @field:SerializedName("timestamp")
        val timestamp: String
)