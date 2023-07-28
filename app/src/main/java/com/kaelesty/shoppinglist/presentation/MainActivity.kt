package com.kaelesty.shoppinglist.presentation

import android.media.MediaRouter.SimpleCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ActivityMainBinding
import com.kaelesty.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var buttonAddShopItem: FloatingActionButton

    private var fragmentContainer: FragmentContainerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initItemTouchHelper()
        initRecycler()
        initButton()

        fragmentContainer = findViewById(R.id.fragmentContainerShopItem)
    }

    private fun initButton() {
        buttonAddShopItem = findViewById(R.id.floatingAddShopItem)
        buttonAddShopItem.setOnClickListener {
            startCreator()
        }
    }

    private fun initRecycler() {
        adapter = ShopListAdapter()

        with(adapter) {
            onLongClick = {
                viewModel.switchShopItemActivation(it)
            }

            onClick = {
                startEditor(it)
            }
        }

        recyclerView = findViewById(R.id.recyclerViewShipList)
        recyclerView.adapter = adapter

        itemTouchHelper.attachToRecyclerView(recyclerView)
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

    private fun startCreator() {
        if (fragmentContainer == null) {
            startActivity(ShopItemActivity.newIntent(this@MainActivity, null))
        }
        else {
            val fragment = ShopItemFragment.newInstance(
                ShopItemActivity.ITEM_NOT_FOUND_VAL
            )
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerShopItem, fragment)
                .commit()
        }
    }

    private fun startEditor(shopItem: ShopItem) {
        if (fragmentContainer == null) {
            startActivity(ShopItemActivity.newIntent(this@MainActivity, shopItem.id))
        }
        else {
            val fragment = ShopItemFragment.newInstance(
                shopItem.id
            )
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerShopItem, fragment)
                .commit()
        }
    }
}