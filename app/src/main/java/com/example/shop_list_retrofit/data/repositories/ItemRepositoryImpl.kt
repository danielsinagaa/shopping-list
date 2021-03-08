package com.example.shop_list_retrofit.data.repositories

import com.example.shop_list_retrofit.data.api.ItemRepository
import com.example.shop_list_retrofit.data.api.RetrofitInstance
import com.example.shop_list_retrofit.data.models.ItemRequest
import com.example.shop_list_retrofit.data.models.ItemResponse
import com.example.shop_list_retrofit.data.models.SingleResponse
import retrofit2.Response

class ItemRepositoryImpl(): ItemRepository {
    override suspend fun getAllShoppingList(): Response<ItemResponse> {
        return RetrofitInstance.shoppingApi.getAllShoppingList()
    }

    override suspend fun addItemShopping(request: ItemRequest): Response<SingleResponse> {
        return RetrofitInstance.shoppingApi.addItemShopping(request)
    }

    override suspend fun deleteItemShopping(id: Int): Response<SingleResponse> {
        return RetrofitInstance.shoppingApi.deleteItemShopping(id)
    }

    override suspend fun findItemById(id: Int): Response<SingleResponse> {
        return RetrofitInstance.shoppingApi.findItemById(id)
    }

    override suspend fun updateItemById(id: Int, request: ItemRequest): Response<SingleResponse> {
        return RetrofitInstance.shoppingApi.updateItemById(id, request)
    }
}