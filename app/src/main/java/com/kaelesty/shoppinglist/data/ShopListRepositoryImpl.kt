package com.kaelesty.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.kaelesty.shoppinglist.di.AppContextQualifier
import com.kaelesty.shoppinglist.domain.ShopItem
import com.kaelesty.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(@AppContextQualifier application: Application) : ShopListRepository {

    @Inject lateinit var dao: ShopListDao


    override suspend fun addShopItem(shopItem: ShopItem) {
        dao.addShopItem(ShopListMapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return MediatorLiveData<List<ShopItem>>().apply {
            addSource(dao.getShopList()) {
                value = ShopListMapper.mapDbModelListToEntityList(it)
            }
        }
    }

    override suspend fun getShopItemById(id: Int): ShopItem? {
        return ShopListMapper.mapDbModelToEntity(dao.getShopItem(id))
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        addShopItem(shopItem) // Items with same ids will be replaced by Room's OnConflictStrategy
    }

    override suspend fun delShopItem(shopItem: ShopItem) {
        dao.delShopItem(shopItem.id)
    }
}