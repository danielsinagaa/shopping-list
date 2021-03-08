package com.example.shop_list_retrofit.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_list_retrofit.R
import com.example.shop_list_retrofit.data.models.ItemEntity
import com.example.shop_list_retrofit.utils.ItemClickListener

class ItemListViewAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ListViewHolder>() {

    private var data : List<ItemEntity> = ArrayList<ItemEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view_item_list,
            parent, false)
        return ListViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val product = data[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = data.size

    fun setItemList(newItemList : List<ItemEntity>) {
        this.data = newItemList
        notifyDataSetChanged()
    }
}