package com.kaelesty.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.kaelesty.shoppinglist.domain.ShopItem
import com.kaelesty.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val _shopList: MutableLiveData<List<ShopItem>> = MutableLiveData()


    private val db = ShopItemDatabase.getInstance(application)
    private val dao = db.shopListDao()


    override fun addShopItem(shopItem: ShopItem) {
        dao.addShopItem(ShopListMapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return MediatorLiveData<List<ShopItem>>().apply {
            addSource(dao.getShopList()) {
                value = ShopListMapper.mapDbModelListToEntityList(it)
            }
        }
    }

    override fun getShopItemById(id: Int): ShopItem? {
        return ShopListMapper.mapDbModelToEntity(dao.getShopItem(id))
    }

    override fun editShopItem(shopItem: ShopItem) {
        addShopItem(shopItem) // Items with same ids will be replaced by Room's OnConflictStrategy
    }

    override fun delShopItem(shopItem: ShopItem) {
        dao.delShopItem(shopItem.id)
    }
}