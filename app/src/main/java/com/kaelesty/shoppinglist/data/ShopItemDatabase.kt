package com.kaelesty.shoppinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class ShopItemDatabase : RoomDatabase() {

	abstract fun shopListDao(): ShopListDao

	companion object {
		private var instance: ShopItemDatabase? = null
		private const val DB_NAME = "shop_items"

		private val LOCK = Any()

		fun getInstance(application: Application): ShopItemDatabase {
			instance?.let {
				return it
			}
			synchronized(LOCK) {
				instance?.let {
					return it
				}
				val db = Room.databaseBuilder(
					application,
					ShopItemDatabase::class.java,
					DB_NAME
				)
					.build()
				instance = db
				return db
			}
		}
	}
}