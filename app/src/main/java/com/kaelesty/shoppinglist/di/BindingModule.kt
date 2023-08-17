package com.kaelesty.shoppinglist.di

import android.view.LayoutInflater
import com.kaelesty.shoppinglist.databinding.ActivityMainBinding
import com.kaelesty.shoppinglist.databinding.ActivityShopItemBinding
import com.kaelesty.shoppinglist.databinding.FragmentShopItemBinding
import dagger.Module
import dagger.Provides

@Module
class BindingModule {

	@Provides
	fun provideMainBinding(layoutInflater: LayoutInflater) =
		ActivityMainBinding.inflate(layoutInflater)

	@Provides
	fun provideShopItemBinding(layoutInflater: LayoutInflater) =
		ActivityShopItemBinding.inflate(layoutInflater)

	@Provides
	fun provideShopItemFragmentBinding(layoutInflater: LayoutInflater) =
		FragmentShopItemBinding.inflate(layoutInflater)
}