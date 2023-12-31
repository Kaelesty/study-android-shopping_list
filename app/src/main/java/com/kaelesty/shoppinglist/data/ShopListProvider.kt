package com.kaelesty.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.kaelesty.shoppinglist.ShopListApp
import com.kaelesty.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopListProvider : ContentProvider() {

	private val component by lazy {
		(context as ShopListApp).component
	}

	@Inject
	lateinit var dao: ShopListDao

	companion object {

		const val AUTHORITY = "com.kaelesty.shoppinglist"
		const val WRONG_QUERY = - 1
		const val GET_SHOP_ITEMS_QUERY = 100

	}

	private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
		addURI(AUTHORITY, "shop_items", GET_SHOP_ITEMS_QUERY)
	}

	override fun onCreate(): Boolean {
		component.inject(this@ShopListProvider)
		return true
	}

	override fun query(
		uri: Uri,
		p1: Array<out String>?,
		p2: String?,
		p3: Array<out String>?,
		p4: String?
	): Cursor? {

		return when (uriMatcher.match(uri)) {
			GET_SHOP_ITEMS_QUERY -> {
				Log.d("ShopListProvider", GET_SHOP_ITEMS_QUERY.toString())
				dao.getShopListCursor()
			}

			else -> {
				Log.d("ShopListProvider", WRONG_QUERY.toString())
				null
			}
		}
	}

	override fun getType(p0: Uri): String? {
		TODO("Not yet implemented")
	}

	override fun insert(uri: Uri, values: ContentValues?): Uri? {
		when (uriMatcher.match(uri)) {
			GET_SHOP_ITEMS_QUERY -> {
				if (values == null) return null

				val shopItem = ShopItem(
					values.getAsString(ShopItemDbModelKeys.NAME_KEY),
					values.getAsInteger(ShopItemDbModelKeys.QUANTITY_KEY),
					values.getAsBoolean(ShopItemDbModelKeys.IS_ACTIVE_KEY),
					values.getAsInteger(ShopItemDbModelKeys.ID_KEY),
				)

				dao.addShopItem(ShopListMapper.mapEntityToDbModel(shopItem))
			}
		}
		return null
	}

	override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
		when(uriMatcher.match(uri)) {
			GET_SHOP_ITEMS_QUERY -> {
				selectionArgs?.get(0)?.toInt()?.let {
					return dao.delShopItem(it)
				}
			}
		}
		return 0
	}

	override fun update(uri: Uri, p1: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
		when(uriMatcher.match(uri)) {
			GET_SHOP_ITEMS_QUERY -> {
				selectionArgs?.let {
					val shopItem = ShopItem(
						it[1], // name
						it[2].toInt(), // count
						it[3] == "true", // enabled
						it[0].toInt(), // id
					)

					dao.addShopItem(ShopListMapper.mapEntityToDbModel(shopItem))
					return 1
				}
			}
		}
		return 0
	}
}