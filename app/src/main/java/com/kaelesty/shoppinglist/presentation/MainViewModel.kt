package com.kaelesty.shoppinglist.presentation

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaelesty.shoppinglist.data.ShopListRepositoryImpl
import com.kaelesty.shoppinglist.domain.DelShopItemUseCase
import com.kaelesty.shoppinglist.domain.EditShopItemUseCase
import com.kaelesty.shoppinglist.domain.GetShopListUseCase
import com.kaelesty.shoppinglist.domain.ShopItem
import com.kaelesty.shoppinglist.domain.ShopListRepository

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val delShopItemUseCase = DelShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList: MutableLiveData<List<ShopItem>> = MutableLiveData()

    fun loadShopList(owner: LifecycleOwner) {
        getShopListUseCase.getShopList().observe(owner) {
            shopList.value = it
        }
    }

    fun delShopItem(shopItem: ShopItem) {
        delShopItemUseCase.delShopItem(shopItem)
    }

    fun switchShopItemActivation(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(isActive = !shopItem.isActive)
        editShopItemUseCase.editShopItem(newShopItem)
    }
}