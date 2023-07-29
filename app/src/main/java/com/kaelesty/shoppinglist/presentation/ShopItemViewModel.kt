package com.kaelesty.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaelesty.shoppinglist.data.ShopListRepositoryImpl
import com.kaelesty.shoppinglist.domain.AddShopItemUseCase
import com.kaelesty.shoppinglist.domain.EditShopItemUseCase
import com.kaelesty.shoppinglist.domain.GetShopItemByIdUseCase
import com.kaelesty.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel(application: Application, private val shopItem: ShopItem?) :
    AndroidViewModel(application) {


    private val _nameError: MutableLiveData<Boolean> = MutableLiveData()
    val nameError: LiveData<Boolean> get() = _nameError

    private val _quantityError: MutableLiveData<Boolean> = MutableLiveData()
    val quantityError: LiveData<Boolean> get() = _quantityError

    private val _quantityToShow: MutableLiveData<String> = MutableLiveData()
    val quantityToShow: LiveData<String> get() = _quantityToShow
    private val _nameToShow: MutableLiveData<String> = MutableLiveData()
    val nameToShow: LiveData<String> get() = _nameToShow

    private val _shouldFinish: MutableLiveData<Unit> = MutableLiveData()
    val shouldFinish: LiveData<Unit> get() = _shouldFinish


    init {
        _nameError.value = false
        _quantityError.value = false


        if (shopItem != null) {
            _nameToShow.value = shopItem.name
            _quantityToShow.value = shopItem.quantity.toString()
        } else {
            _nameToShow.value = ""
            _quantityToShow.value = ""
        }
    }

    companion object {

        private val addShopItemUseCase = AddShopItemUseCase(ShopListRepositoryImpl)
        private val getShopItemUseCase = GetShopItemByIdUseCase(ShopListRepositoryImpl)
        private val editShopItemUseCase = EditShopItemUseCase(ShopListRepositoryImpl)
        fun createShopItem(itemId: Int): ShopItem? {
            if (itemId == ShopItemActivity.ITEM_NOT_FOUND_VAL) {
                return null
            }
            return getShopItemUseCase.getShopItemById(itemId)
        }
    }

    fun save(inputName: String, inputQuantity: String) {
        val name = parseName(inputName)
        var returnFlag = false
        if (!validateName(name)) {
            _nameError.value = true
            returnFlag = true
        } else {
            _nameError.value = false
        }

        val quantity = parseQuantity(inputQuantity)
        if (!validateQuantity(quantity)) {
            _quantityError.value = true
            returnFlag = true
        } else {
            _quantityError.value = false
        }
        if (returnFlag) {
            return
        }
        if (shopItem != null) {
            editShopItem(
                ShopItem(name, quantity, shopItem.isActive, shopItem.id)
            )
        } else {
            addShopItem(
                ShopItem(name, quantity, true)
            )
        }
        _shouldFinish.value = Unit
    }

    private fun addShopItem(shopItem: ShopItem) {
        addShopItemUseCase.addShopItem(
            shopItem
        )
    }


    private fun editShopItem(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(
            shopItem
        )
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseQuantity(quantity: String?): Int {
        return try {
            quantity?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateName(name: String) = name.isNotBlank()
    private fun validateQuantity(quantity: Int) = quantity > 0
}