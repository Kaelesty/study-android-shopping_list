package com.kaelesty.shoppinglist.data

import com.kaelesty.shoppinglist.domain.ShopItem

object ShopListMapper {

	fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
		shopItem.id,
		shopItem.name,
		shopItem.quantity,
		shopItem.isActive
	)

	fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel?): ShopItem? {
		shopItemDbModel?.let {
			return ShopItem(
				shopItemDbModel.name,
				shopItemDbModel.quantity,
				shopItemDbModel.isActive,
				shopItemDbModel.id
			)
		}
		return null
	}

	fun mapDbModelListToEntityList(list: List<ShopItemDbModel>): List<ShopItem> = list.map {
		ShopItem(
			it.name,
			it.quantity,
			it.isActive,
			it.id
		)
	}
}