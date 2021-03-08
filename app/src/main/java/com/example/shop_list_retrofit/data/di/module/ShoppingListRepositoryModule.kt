package com.example.shop_list_retrofit.data.di.module

import com.example.shop_list_retrofit.data.api.ItemRepository
import com.example.shop_list_retrofit.data.di.qualifier.ShoppingListRepo
import com.example.shop_list_retrofit.data.repositories.ItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ShoppingListRepoModule {

    @Binds
    @ShoppingListRepo
    abstract fun bindsRepoShoppingList(itemRepositoriesImpl: ItemRepositoryImpl): ItemRepository
}