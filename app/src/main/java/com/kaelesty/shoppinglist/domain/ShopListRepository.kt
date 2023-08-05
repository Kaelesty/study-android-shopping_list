package com.kaelesty.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun getShopItemById(id: Int): ShopItem?

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun delShopItem(shopItem: ShopItem)

}