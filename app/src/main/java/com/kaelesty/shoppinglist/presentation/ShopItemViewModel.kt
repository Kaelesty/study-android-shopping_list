package com.kaelesty.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaelesty.shoppinglist.data.ShopListRepositoryImpl
import com.kaelesty.shoppinglist.domain.AddShopItemUseCase
import com.kaelesty.shoppinglist.domain.EditShopItemUseCase
import com.kaelesty.shoppinglist.domain.GetShopItemByIdUseCase
import com.kaelesty.shoppinglist.domain.GetShopListUseCase
import com.kaelesty.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val addShopItemUseCase = AddShopItemUseCase(ShopListRepositoryImpl)
    private val getShopItemUseCase = GetShopItemByIdUseCase(ShopListRepositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(ShopListRepositoryImpl)

    val nameError: MutableLiveData<Boolean> = MutableLiveData()
    val quanityError: MutableLiveData<Boolean> = MutableLiveData()

    init {
        nameError.value = false
        quanityError.value = false
    }

    fun addShopItem(inputName: String?, inputQuanity: String?) {
        val name = parseName(inputName)
        var returnFlag = false
        if (!validateName(name)) {
            // make error message
            returnFlag = true
        }

        val quanity = parseQuanity(inputQuanity)
        if(!validateQuanity(quanity)) {
            nameError.value = true
            returnFlag = true
        }
        if (returnFlag) {
            quanityError.value = true
            return
        }
        addShopItemUseCase.addShopItem(
            ShopItem(name, quanity, true)
        )
    }

    fun getShopItem(id: Int): ShopItem {
        return getShopItemUseCase.getShopItemById(id)
    }

    fun editShopItem(id: Int, inputName: String?, inputQuanity: String?) {
        val name = parseName(inputName)
        var returnFlag = false
        if (!validateName(name)) {
            // make error message
            returnFlag = true
        }

        val quanity = parseQuanity(inputQuanity)
        if(!validateQuanity(quanity)) {
            nameError.value = true
            returnFlag = true
        }
        if (returnFlag) {
            quanityError.value = true
            return
        }
        val shopItem = getShopItemUseCase.getShopItemById(id)
        editShopItemUseCase.editShopItem(
            ShopItem(name, quanity, shopItem.isActive, shopItem.id)
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