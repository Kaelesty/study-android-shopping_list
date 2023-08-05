package com.kaelesty.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelesty.shoppinglist.data.ShopListRepositoryImpl
import com.kaelesty.shoppinglist.domain.AddShopItemUseCase
import com.kaelesty.shoppinglist.domain.DelShopItemUseCase
import com.kaelesty.shoppinglist.domain.EditShopItemUseCase
import com.kaelesty.shoppinglist.domain.GetShopItemByIdUseCase
import com.kaelesty.shoppinglist.domain.GetShopListUseCase
import com.kaelesty.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    private val repo: ShopListRepositoryImpl

    private val getShopListUseCase: GetShopListUseCase
    private val delShopItemUseCase: DelShopItemUseCase
    private val editShopItemUseCase: EditShopItemUseCase

    private val _shopList: MutableLiveData<List<ShopItem>> = MutableLiveData()
    val shopList: LiveData<List<ShopItem>> get() = _shopList

    init {

        repo = ShopListRepositoryImpl(application)

        delShopItemUseCase = DelShopItemUseCase(repo)
        getShopListUseCase = GetShopListUseCase(repo)
        editShopItemUseCase = EditShopItemUseCase(repo)
    }

    fun loadShopList(owner: LifecycleOwner) {
        getShopListUseCase.getShopList().observe(owner) {
            _shopList.value = it
        }
    }

    fun delShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            delShopItemUseCase.delShopItem(shopItem)
        }
    }

    fun switchShopItemActivation(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(isActive = !shopItem.isActive)
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }
}