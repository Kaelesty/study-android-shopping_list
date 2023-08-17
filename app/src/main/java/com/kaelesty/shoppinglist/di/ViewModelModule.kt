package com.kaelesty.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.kaelesty.shoppinglist.presentation.MainViewModel
import com.kaelesty.shoppinglist.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

	@IntoMap
	@ViewModelKey(MainViewModel::class)
	@Binds
	fun bindMainViewModel(impl: MainViewModel): ViewModel

	@IntoMap
	@ViewModelKey(ShopItemViewModel::class)
	@Binds
	fun bindShopItemViewModel(impl: ShopItemViewModel): ViewModel
}