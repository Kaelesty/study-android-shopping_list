package com.kaelesty.shoppinglist.presentation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.ShopListApp
import com.kaelesty.shoppinglist.data.ShopItemDbModel
import com.kaelesty.shoppinglist.databinding.ActivityMainBinding
import com.kaelesty.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShopItemFragment.Companion.OnEditingFinishedListener {


	@Inject
	lateinit var viewModel: MainViewModel
	private lateinit var itemTouchHelper: ItemTouchHelper

	@Inject
	lateinit var binding: ActivityMainBinding

	@Inject
	lateinit var adapter: ShopListAdapter

	private val component by lazy {
		(application as ShopListApp).component
			.activityComponentFactory()
			.create(layoutInflater)
	}


	override fun onCreate(savedInstanceState: Bundle?) {

		component.inject(this@MainActivity)

		super.onCreate(savedInstanceState)

		setContentView(binding.root)

		initViewModel()
		initItemTouchHelper()
		initRecycler()
		initButton()

		thread {
			val cursor = contentResolver.query(
				Uri.parse("content://com.kaelesty.shoppinglist/shop_items"),
				null,
				null,
				null,
				null,
				null,
			)

			while (cursor?.moveToNext() != null) {
				val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
				val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
				val count = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
				val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("isActive")) > 0
				// it cant work with booleans. int > 0 means true
				val shopItemDbModel = ShopItemDbModel(
					id, name, count, enabled
				)


				Log.d("ShopListProvider", shopItemDbModel.toString())
			}
		}
	}

	private fun initButton() {
		binding.floatingAddShopItem.setOnClickListener {
			launchFragment()
		}
	}

	private fun initRecycler() {

		with(adapter) {
			onLongClick = {
				viewModel.switchShopItemActivation(it)
			}

			onClick = {
				launchFragment(it)
			}
		}

		binding.recyclerViewShipList.adapter = adapter

		itemTouchHelper.attachToRecyclerView(binding.recyclerViewShipList)
	}

	private fun initViewModel() {

		viewModel.loadShopList(this)

		viewModel.shopList.observe(this) {
			adapter.submitList(it.toMutableList())
		}
	}

	private fun initItemTouchHelper() {
		itemTouchHelper = ItemTouchHelper(
			object : ItemTouchHelper.SimpleCallback(
				0,
				ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
			) {
				override fun onMove(
					recyclerView: RecyclerView,
					viewHolder: RecyclerView.ViewHolder,
					target: RecyclerView.ViewHolder
				): Boolean {
					return false
				}

				override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
					viewModel.delShopItem(adapter.currentList[viewHolder.adapterPosition])
				}
			}
		)
	}

	override fun onEditingFinished() {
		supportFragmentManager.popBackStack()
	}

	private fun launchFragment(shopItem: ShopItem? = null) {
		if (binding.fragmentContainerShopItem == null) {
			startActivity(
				ShopItemActivity.newIntent(
					this@MainActivity,
					shopItem?.id ?: ShopItemActivity.ITEM_NOT_FOUND_VAL
				)
			)
		} else {
			val fragment = ShopItemFragment.newInstance(
				shopItem?.id ?: ShopItemActivity.ITEM_NOT_FOUND_VAL
			)

			supportFragmentManager.popBackStack() // To delete prev fragment if it exist
			supportFragmentManager
				.beginTransaction()
				.replace(R.id.fragmentContainerShopItem, fragment)
				.addToBackStack(null) // to make fragment closable by pressing "back" instead of activity
				.commit()
		}
	}
}