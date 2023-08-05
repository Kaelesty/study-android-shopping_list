package com.kaelesty.shoppinglist.domain

class DelShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun delShopItem(shopItem: ShopItem) {
        shopListRepository.delShopItem(shopItem)
    }
}