package com.example.shop_list_retrofit.data.di.module

import com.example.shop_list_retrofit.data.api.ShopApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShoppingListApiModule {
    @Singleton
    @Provides
    fun provideShoppingListApi(retrofit : Retrofit)  = retrofit.create(ShopApi::class.java)
}