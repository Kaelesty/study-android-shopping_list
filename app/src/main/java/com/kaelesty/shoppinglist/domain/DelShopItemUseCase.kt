package com.kaelesty.shoppinglist.domain

class DelShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun delShopItem(shopItem: ShopItem) {
        shopListRepository.delShopItem(shopItem)
    }
}