package com.kaelesty.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShopItemVMFactory(val application: Application, val itemId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShopItemViewModel(application, ShopItemViewModel.createShopItem(itemId)) as T
    }
}