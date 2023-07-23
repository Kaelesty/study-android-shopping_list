package com.kaelesty.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>

    fun getShopItemById(id: Int): ShopItem

    fun editShopItem(shopItem: ShopItem)

    fun delShopItem(shopItem: ShopItem)

}