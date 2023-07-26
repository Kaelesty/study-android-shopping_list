package com.kaelesty.shoppinglist.presentation

import android.media.MediaRouter.SimpleCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var buttonAddShopItem: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initItemTouchHelper()
        initRecycler()
        initButton()
    }

    private fun initButton() {
        buttonAddShopItem = findViewById(R.id.floatingAddShopItem)
        buttonAddShopItem.setOnClickListener {
            startActivity(ShopItemActivity.newIntent(this@MainActivity, null))
        }
    }

    private fun initRecycler() {
        adapter = ShopListAdapter()

        with(adapter) {
            onLongClick = {
                viewModel.switchShopItemActivation(it)
            }

            onClick = {
                startActivity(ShopItemActivity.newIntent(this@MainActivity, it.id))
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
}