package com.kaelesty.shoppinglist.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaelesty.shoppinglist.domain.ShopItem

@Dao
interface ShopListDao {

	@Query("SELECT * FROM shop_items")
	fun getShopList(): LiveData<List<ShopItemDbModel>>

	@Query("SELECT * FROM shop_items")
	fun getShopListCursor(): Cursor

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addShopItem(shopItem: ShopItemDbModel)

	@Query("DELETE FROM shop_items WHERE id = :shopItemId")
	fun delShopItem(shopItemId: Int)

	@Query("SELECT * FROM shop_items WHERE id = :shopItemId LIMIT 1")
	fun getShopItem(shopItemId: Int): ShopItemDbModel?

	@Query("SELECT * FROM shop_items WHERE id = :shopItemId LIMIT 1")
	fun getShopItemCursor(shopItemId: Int): Cursor
}