package com.kaelesty.shoppinglist

import android.app.Application
import com.kaelesty.shoppinglist.di.DaggerApplicationComponent


class ShopListApp: Application() {

	val component by lazy {
		DaggerApplicationComponent.factory().create(this)
	}
}