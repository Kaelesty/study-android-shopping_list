package com.kaelesty.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.kaelesty.shoppinglist.ShopListApp
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

	private val component by lazy {
		(context as ShopListApp).component
	}

	@Inject lateinit var dao: ShopListDao

	companion object {

		const val AUTHORITY = "com.kaelesty.shoppinglist"
		const val WRONG_QUERY = -1
		const val GET_SHOP_ITEMS_QUERY = 100
		const val GET_SHOP_ITEM_BYID_QUERY = 101
	}

	private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
		addURI(AUTHORITY, "shop_items", GET_SHOP_ITEMS_QUERY)
		addURI(AUTHORITY, "shop_items/*", GET_SHOP_ITEM_BYID_QUERY)
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

		val code = uriMatcher.match(uri)
		return when (code) {
			GET_SHOP_ITEMS_QUERY -> {
				Log.d("ShopListProvider", GET_SHOP_ITEMS_QUERY.toString())
				dao.getShopListCursor()
			}

			GET_SHOP_ITEM_BYID_QUERY -> {
				Log.d("ShopListProvider", GET_SHOP_ITEM_BYID_QUERY.toString())
				null
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

	override fun insert(p0: Uri, p1: ContentValues?): Uri? {
		TODO("Not yet implemented")
	}

	override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
		TODO("Not yet implemented")
	}

	override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
		TODO("Not yet implemented")
	}
}