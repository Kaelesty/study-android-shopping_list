package com.kaelesty.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaelesty.shoppinglist.di.AppContextQualifier
import com.kaelesty.shoppinglist.domain.AddShopItemUseCase
import com.kaelesty.shoppinglist.domain.EditShopItemUseCase
import com.kaelesty.shoppinglist.domain.GetShopItemByIdUseCase
import com.kaelesty.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    @AppContextQualifier application: Application,
    var addShopItemUseCase: AddShopItemUseCase,
    var getShopItemUseCase: GetShopItemByIdUseCase,
    var editShopItemUseCase: EditShopItemUseCase
    ) :
    AndroidViewModel(application) {

    private var shopItem: ShopItem? = null

    private val scope = CoroutineScope(Dispatchers.IO)

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


    fun setShopItem(shopItemId: Int) {
        _nameError.value = false
        _quantityError.value = false

        scope.launch {
            shopItem =
                if (shopItemId != ShopItemFragment.ITEM_NOT_FOUND_VAL)
                    getShopItemUseCase.getShopItemById(shopItemId)
                else null
            if (shopItem != null) {
                _nameToShow.postValue((shopItem as ShopItem).name)
                _quantityToShow.postValue((shopItem as ShopItem).quantity.toString())
            } else {
                _nameToShow.postValue("")
                _quantityToShow.postValue("")
            }
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
        scope.launch {
            if (shopItem != null) {
                editShopItem(
                    ShopItem(name, quantity, (shopItem as ShopItem).isActive, (shopItem as ShopItem).id)
                )
            } else {
                addShopItem(
                    ShopItem(name, quantity, true)
                )
            }
        }
        _shouldFinish.value = Unit
    }

    private fun addShopItem(shopItem: ShopItem) {
        scope.launch {
            addShopItemUseCase.addShopItem(
                shopItem
            )
        }
    }


    private fun editShopItem(shopItem: ShopItem) {
        scope.launch {
            editShopItemUseCase.editShopItem(
                shopItem
            )
        }
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