package com.kaelesty.shoppinglist.di

import android.view.LayoutInflater
import com.kaelesty.shoppinglist.presentation.MainActivity
import com.kaelesty.shoppinglist.presentation.ShopItemActivity
import com.kaelesty.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [BindingModule::class])
interface ActivityComponent {

	fun inject(activity: MainActivity)

	fun inject(activity: ShopItemActivity)

	fun inject(fragment: ShopItemFragment)

	@Subcomponent.Factory
	interface Factory {

		fun create(
			@BindsInstance layoutInflater: LayoutInflater
		): ActivityComponent
	}
}