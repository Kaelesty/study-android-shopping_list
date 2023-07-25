package com.kaelesty.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kaelesty.shoppinglist.R

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShopItemActivity::class.java)
        }
    }
}