package com.kaelesty.shoppinglist.di

import android.app.Application
import com.kaelesty.shoppinglist.data.ShopItemDatabase
import dagger.BindsInstance
import dagger.Module
import dagger.Provides

@Module
class DatasourceModule {

	@Provides
	fun provideDatabase(@AppContextQualifier application: Application) =
		ShopItemDatabase.getInstance(application)

	@Provides
	fun provideDao(db: ShopItemDatabase) = db.shopListDao()
}