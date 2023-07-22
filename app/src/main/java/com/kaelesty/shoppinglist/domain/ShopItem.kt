package com.kaelesty.shoppinglist.domain

const val SHOP_ITEM_EMPTY_ID = -1

data class ShopItem(
    val name: String,
    val quanity: Int,
    val isActive: Boolean,
    var id: Int = SHOP_ITEM_EMPTY_ID,
)
