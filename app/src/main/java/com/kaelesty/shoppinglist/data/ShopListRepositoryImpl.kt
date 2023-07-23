package com.kaelesty.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaelesty.shoppinglist.domain.ShopItem
import com.kaelesty.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList: MutableLiveData<List<ShopItem>> = MutableLiveData()
    private var autoIncrement = 0

    override fun addShopItem(shopItem: ShopItem) {
        shopItem.id = autoIncrement
        autoIncrement++
        val newShopList = shopList.value
        newShopList as MutableList
        newShopList.add(shopItem)
        shopList.value = newShopList
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopList
    }

    override fun getShopItemById(id: Int): ShopItem {
        val newShopList = shopList.value
        return newShopList?.find { it.id == id } ?:
        throw RuntimeException("Shop item wasn't found (by id)")
    }

    override fun editShopItem(shopItem: ShopItem) {
        val newShopList = shopList.value
        newShopList as MutableList
        val oldElem = getShopItemById(shopItem.id)
        newShopList.remove(oldElem)
        newShopList.add(shopItem)
        shopList.value = newShopList
    }

    override fun delShopItem(shopItem: ShopItem) {
        val newShopList = shopList.value
        newShopList as MutableList
        newShopList.remove(shopItem)
        shopList.value = newShopList
    }
}