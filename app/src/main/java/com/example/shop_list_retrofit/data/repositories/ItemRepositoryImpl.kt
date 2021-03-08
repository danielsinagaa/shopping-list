package com.example.shop_list_retrofit.data.repositories

import com.example.shop_list_retrofit.data.api.ItemRepository
import com.example.shop_list_retrofit.data.api.RetrofitInstance
import com.example.shop_list_retrofit.data.api.ShopApi
import com.example.shop_list_retrofit.data.models.ItemRequest
import com.example.shop_list_retrofit.data.models.ItemResponse
import com.example.shop_list_retrofit.data.models.SingleResponse
import retrofit2.Response
import javax.inject.Inject

class ItemRepositoryImpl@Inject constructor(private val shopApi: ShopApi): ItemRepository {
    override suspend fun getAllShoppingList(): Response<ItemResponse> {
        return shopApi.getAllShoppingList()
    }

    override suspend fun addItemShopping(request: ItemRequest): Response<SingleResponse> {
        return shopApi.addItemShopping(request)
    }

    override suspend fun deleteItemShopping(id: Int): Response<SingleResponse> {
        return shopApi.deleteItemShopping(id)
    }

    override suspend fun findItemById(id: Int): Response<SingleResponse> {
        return shopApi.findItemById(id)
    }

    override suspend fun updateItemById(id: Int, request: ItemRequest): Response<SingleResponse> {
        return shopApi.updateItemById(id, request)
    }
}