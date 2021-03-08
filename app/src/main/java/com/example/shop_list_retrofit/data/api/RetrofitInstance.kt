package com.example.shop_list_retrofit.data.api

import com.example.shop_list_retrofit.utils.CONSTANT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl(CONSTANT.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val shoppingApi : ShopApi by lazy {
        retrofit.create(ShopApi::class.java)
    }
}