package com.example.shop_list_retrofit.data.api

import com.example.shop_list_retrofit.data.models.ItemRequest
import com.example.shop_list_retrofit.data.models.ItemResponse
import com.example.shop_list_retrofit.data.models.SingleResponse
import retrofit2.Response
import retrofit2.http.*

interface ShopApi {
    @GET("/shopping")
    suspend fun getAllShoppingList(): Response<ItemResponse>

    @POST("/shopping")
    suspend fun addItemShopping(@Body request : ItemRequest): Response<SingleResponse>

    @DELETE("/shopping/{id}")
    suspend fun deleteItemShopping(@Path("id") id : Int) : Response<SingleResponse>

    @GET("/shopping/{id}")
    suspend fun findItemById(@Path("id") id : Int) : Response<SingleResponse>

    @PUT("/shopping/{id}")
    suspend fun updateItemById(@Path("id") id :Int, @Body request : ItemRequest) : Response<SingleResponse>
}