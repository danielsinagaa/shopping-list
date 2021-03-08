package com.example.shop_list_retrofit.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ItemEntity (
    val id: Int,
    val itemName: String,
    val dateCreated: String,
    val quantity: Int,
    val price: Int,
    val note: String
        ) : Parcelable