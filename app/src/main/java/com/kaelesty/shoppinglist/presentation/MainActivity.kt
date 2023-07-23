package com.kaelesty.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()

        initViewModel()

    }

    private fun initRecycler() {
        adapter = ShopListAdapter()
        recyclerView = findViewById(R.id.recyclerViewShipList)
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.loadShopList(this)

        viewModel.shopList.observe(this) {
            adapter.shopList = it
        }
    }
}