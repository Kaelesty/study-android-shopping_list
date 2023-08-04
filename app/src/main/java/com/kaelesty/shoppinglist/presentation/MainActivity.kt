package com.kaelesty.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ActivityMainBinding
import com.kaelesty.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity(), ShopItemFragment.Companion.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initItemTouchHelper()
        initRecycler()
        initButton()
    }

    private fun initButton() {
        binding.floatingAddShopItem.setOnClickListener {
            launchFragment()
        }
    }

    private fun initRecycler() {
        adapter = ShopListAdapter()

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
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.loadShopList(this)

        viewModel.shopList.observe(this) {
            adapter.submitList(it.toMutableList())
        }
    }

    private fun initItemTouchHelper() {
        itemTouchHelper = ItemTouchHelper(
            object: ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
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
                    shopItem?.id?:ShopItemActivity.ITEM_NOT_FOUND_VAL
                )
            )
        }
        else {
            val fragment = ShopItemFragment.newInstance(
                shopItem?.id?:ShopItemActivity.ITEM_NOT_FOUND_VAL
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