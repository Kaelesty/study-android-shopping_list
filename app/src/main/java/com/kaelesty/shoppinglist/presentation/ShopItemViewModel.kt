package com.kaelesty.shoppinglist.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.kaelesty.shoppinglist.data.ShopListRepositoryImpl
import com.kaelesty.shoppinglist.domain.AddShopItemUseCase
import com.kaelesty.shoppinglist.domain.EditShopItemUseCase
import com.kaelesty.shoppinglist.domain.GetShopItemByIdUseCase
import com.kaelesty.shoppinglist.domain.GetShopListUseCase
import com.kaelesty.shoppinglist.domain.SHOP_ITEM_EMPTY_ID
import com.kaelesty.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel(application: Application, val shopItem: ShopItem?) : AndroidViewModel(application) {


    private val _nameError: MutableLiveData<Boolean> = MutableLiveData()
    val nameError: LiveData<Boolean> get() = _nameError as LiveData<Boolean>

    private val _quanityError: MutableLiveData<Boolean> = MutableLiveData()
    val quanityError: LiveData<Boolean> get() = _quanityError as LiveData<Boolean>

    private val _quanityToShow: MutableLiveData<String> = MutableLiveData()
    val quanityToShow: LiveData<String> get() = _quanityToShow as LiveData<String>

    private val _nameToShow: MutableLiveData<String> = MutableLiveData()
    val nameToShow: LiveData<String> get() = _nameToShow as LiveData<String>

    private val _shouldFinish: MutableLiveData<Unit> = MutableLiveData()
    val shouldFinish: LiveData<Unit> get() = _shouldFinish as LiveData<Unit>


    init {
        _nameError.value = false
        _quanityError.value = false


        if (shopItem != null) {
            _nameToShow.value = shopItem.name
            _quanityToShow.value = shopItem.quanity.toString()
        }
        else {
            _nameToShow.value = ""
            _quanityToShow.value = ""
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

    fun save(inputName: String, inputQuanity: String) {
        val name = parseName(inputName)
        var returnFlag = false
        if (!validateName(name)) {
            _nameError.value = true
            returnFlag = true
        }
        else {
            _nameError.value = false
        }

        val quanity = parseQuanity(inputQuanity)
        if(!validateQuanity(quanity)) {
            _quanityError.value = true
            returnFlag = true
        }
        else {
            _quanityError.value = false
        }
        if (returnFlag) {
            return
        }
        if (shopItem != null) {
            editShopItem(
                ShopItem(name, quanity, shopItem.isActive, shopItem.id)
            )
        }
        else {
            addShopItem(
                ShopItem(name, quanity, true)
            )
        }
        _shouldFinish.value = Unit
    }

    private fun addShopItem(shopItem: ShopItem) {
        addShopItemUseCase.addShopItem(
            shopItem
        )
    }

    private fun getShopItem(id: Int): ShopItem {
        return getShopItemUseCase.getShopItemById(id)
    }

    private fun editShopItem(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(
            shopItem
        )
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseQuanity(quanity: String?): Int {
        return try {
            quanity?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateName(name: String) = name.isNotBlank()
    private fun validateQuanity(quanity: Int) = quanity>0
}