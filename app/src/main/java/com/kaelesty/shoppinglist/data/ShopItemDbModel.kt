package com.kaelesty.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kaelesty.shoppinglist.domain.SHOP_ITEM_EMPTY_ID
import com.kaelesty.shoppinglist.domain.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemDbModel(
	@PrimaryKey(autoGenerate = true) var id: Int = SHOP_ITEM_EMPTY_ID,

	val name: String,
	val quantity: Int,
	val isActive: Boolean,

)