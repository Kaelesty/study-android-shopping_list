package com.kaelesty.shoppinglist.data

import com.kaelesty.shoppinglist.domain.ShopItem
import com.kaelesty.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList: ArrayList<ShopItem> = ArrayList()
    private var autoIncrement = 0

    override fun addShopItem(shopItem: ShopItem) {
        shopItem.id = autoIncrement
        autoIncrement++
        shopList.add(shopItem)
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItemById(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw ArrayStoreException()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElem = shopList.find { it.id == shopItem.id } ?: throw ArrayStoreException()
        shopList.remove(oldElem)
        shopList.add(shopItem)
    }

    override fun delShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }
}