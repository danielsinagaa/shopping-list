package com.example.shop_list_retrofit.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_list_retrofit.data.models.ItemEntity
import com.example.shop_list_retrofit.databinding.CardViewItemListBinding
import com.example.shop_list_retrofit.utils.ItemClickListener

class ListViewHolder(view: View, private val listener: ItemClickListener) : RecyclerView.ViewHolder(view) {
    private val binding = CardViewItemListBinding.bind(view)

    fun bind(item: ItemEntity) {
        val dateString = item.dateCreated.substring(0,10)
        binding.apply {
            itemView.text = item.itemName
            quantityView.text = "${item.quantity}"
            priceView.text = "${item.price}"
            noteView.text = item.note
            dateView.text = dateString
            deleteView.setOnClickListener {
                listener.onDelete(item)
            }
            editView.setOnClickListener {
                listener.onUpdate(item)
            }
        }


    }
}