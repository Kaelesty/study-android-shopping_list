package com.kaelesty.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ShopItemActiveBinding
import com.kaelesty.shoppinglist.databinding.ShopItemUnactiveBinding
import com.kaelesty.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(

    object : DiffUtil.ItemCallback<ShopItem>() {
        override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem == newItem
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
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = currentList[position]

        with(holder.binding) {

            when (this) {
                is ShopItemUnactiveBinding -> {
                    textViewShopItemTitle.text = shopItem.name
                    textViewShopItemQuanity.text = shopItem.quantity.toString()
                }
                is ShopItemActiveBinding -> {
                    textViewShopItemTitle.text = shopItem.name
                    textViewShopItemQuanity.text = shopItem.quantity.toString()
                }
            }

            onLongClick?.let {
                root.setOnLongClickListener {
                    it(shopItem)
                    false
                }
            }

            onClick?.let {
                root.setOnClickListener {
                    it(shopItem)
                }
            }
        }
    }

    inner class ShopItemViewHolder(val binding: ViewDataBinding) : ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isActive) VIEW_TYPE_ACTIVE else VIEW_TYPE_UNACTIVE
    }
}