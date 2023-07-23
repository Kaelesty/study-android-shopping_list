package com.kaelesty.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ShopItemActiveBinding
import com.kaelesty.shoppinglist.databinding.ShopItemUnactiveBinding
import com.kaelesty.shoppinglist.domain.ShopItem

class ShopListAdapter: Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object {
        val VIEW_TYPE_ACTIVE = R.layout.shop_item_active
        val VIEW_TYPE_UNACTIVE = R.layout.shop_item_unactive
    }

    var shopList: List<ShopItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        return ShopItemViewHolder(
            LayoutInflater.from(parent.context).inflate
                (viewType,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.textViewTitle.text = shopItem.name
        holder.textViewQuanity.text = shopItem.quanity.toString()

    }

    inner class ShopItemViewHolder(val itemView: View): ViewHolder(itemView) {
        var textViewTitle = itemView.findViewById<TextView>(R.id.textViewShopItemTitle)
        var textViewQuanity = itemView.findViewById<TextView>(R.id.textViewShopItemQuanity)
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].isActive) VIEW_TYPE_ACTIVE else VIEW_TYPE_UNACTIVE
    }
}