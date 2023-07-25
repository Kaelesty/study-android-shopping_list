package com.kaelesty.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ShopItemActiveBinding
import com.kaelesty.shoppinglist.databinding.ShopItemUnactiveBinding
import com.kaelesty.shoppinglist.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(

    object: DiffUtil.ItemCallback<ShopItem>() {
        override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem)
            = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem)
            = oldItem == newItem
    }

) {

    companion object {
        val VIEW_TYPE_ACTIVE = R.layout.shop_item_active
        val VIEW_TYPE_UNACTIVE = R.layout.shop_item_unactive
    }

    var onLongClick: ((ShopItem) -> Unit)? = null
    var onClick: ((ShopItem) -> Unit)? = null
    var onSwipe: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        return ShopItemViewHolder(
            LayoutInflater.from(parent.context).inflate
                (viewType,
                parent,
                false)
        )
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = currentList[position]
        holder.textViewTitle.text = shopItem.name
        holder.textViewQuanity.text = shopItem.quanity.toString()

        onLongClick?.let {
            holder.view.setOnLongClickListener {
                it(shopItem)
                false
            }
        }

        onClick?.let {
            holder.view.setOnClickListener {
                it(shopItem)
            }
        }

    }

    fun setData(newData: List<ShopItem>) {
        submitList(newData)
    }

    inner class ShopItemViewHolder(itemView: View): ViewHolder(itemView) {

        val view = itemView
        var textViewTitle = itemView.findViewById<TextView>(R.id.textViewShopItemTitle)
        var textViewQuanity = itemView.findViewById<TextView>(R.id.textViewShopItemQuanity)
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isActive) VIEW_TYPE_ACTIVE else VIEW_TYPE_UNACTIVE
    }
}