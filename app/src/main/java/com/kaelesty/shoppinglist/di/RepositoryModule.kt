package com.kaelesty.shoppinglist.di

import com.kaelesty.shoppinglist.data.ShopListRepositoryImpl
import com.kaelesty.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

	@Binds
	fun bindsRepository(impl: ShopListRepositoryImpl): ShopListRepository
}