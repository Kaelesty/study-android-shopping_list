package com.kaelesty.shoppinglist.domain

import javax.inject.Inject

class GetShopItemByIdUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun getShopItemById(id: Int): ShopItem? {
        return shopListRepository.getShopItemById(id)
    }
}