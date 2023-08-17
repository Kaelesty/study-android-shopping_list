package com.kaelesty.shoppinglist.di

import android.app.Application
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

	@Component.Factory
	interface ApplicationComponentFactory {

		fun create(
			@BindsInstance @AppContextQualifier application: Application
		): ApplicationComponent
	}
}