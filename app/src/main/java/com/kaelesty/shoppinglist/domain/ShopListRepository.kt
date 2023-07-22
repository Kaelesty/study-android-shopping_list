package com.kaelesty.shoppinglist.domain

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun getShopList(): List<ShopItem>

    fun getShopItemById(id: Int): ShopItem

    fun editShopItem(shopItem: ShopItem)

    fun delShopItem(shopItem: ShopItem)

}