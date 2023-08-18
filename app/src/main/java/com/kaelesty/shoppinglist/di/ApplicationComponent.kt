package com.kaelesty.shoppinglist.di

import android.app.Application
import com.kaelesty.shoppinglist.data.ShopListProvider
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
	modules = [
		DatasourceModule::class,
		RepositoryModule::class,
		ViewModelModule::class
	]
)
interface ApplicationComponent {

	fun activityComponentFactory(): ActivityComponent.Factory

	fun inject(provider: ShopListProvider)

	@Component.Factory
	interface ApplicationComponentFactory {

		fun create(
			@BindsInstance @AppContextQualifier application: Application
		): ApplicationComponent
	}
}