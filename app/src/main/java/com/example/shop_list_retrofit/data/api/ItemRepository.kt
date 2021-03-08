package com.example.shop_list_retrofit.data.api

import com.example.shop_list_retrofit.data.models.ItemRequest
import com.example.shop_list_retrofit.data.models.ItemResponse
import com.example.shop_list_retrofit.data.models.SingleResponse
import retrofit2.Response
import retrofit2.http.Body

interface ItemRepository {
    suspend fun getAllShoppingList(): Response<ItemResponse>
    suspend fun addItemShopping(request : ItemRequest): Response<SingleResponse>
    suspend fun deleteItemShopping(id : Int) : Response<SingleResponse>
    suspend fun findItemById(id : Int) : Response<SingleResponse>
    suspend fun updateItemById( id :Int, @Body request : ItemRequest) : Response<SingleResponse>
}