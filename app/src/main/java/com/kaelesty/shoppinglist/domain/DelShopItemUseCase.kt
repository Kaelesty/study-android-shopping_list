package com.kaelesty.shoppinglist.domain

import javax.inject.Inject

class DelShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun delShopItem(shopItem: ShopItem) {
        shopListRepository.delShopItem(shopItem)
    }
}