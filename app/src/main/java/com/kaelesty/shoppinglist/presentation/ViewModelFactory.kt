package com.kaelesty.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaelesty.shoppinglist.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelFactory @Inject constructor(
	private val viewModels: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

	// @ApplicationScope means that we got only one instance of Factory to entire app
	// we use Provider<ViewModel> to avoid using one object of viewModel on all places

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return viewModels[modelClass]?.get() as T
	}
}