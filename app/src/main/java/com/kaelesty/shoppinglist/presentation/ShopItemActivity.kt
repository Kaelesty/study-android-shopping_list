package com.kaelesty.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kaelesty.shoppinglist.R
import com.kaelesty.shoppinglist.databinding.ActivityShopItemBinding
import com.kaelesty.shoppinglist.databinding.ShopItemActiveBinding

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.Companion.OnEditingFinishedListener {

    private lateinit var binding: ActivityShopItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            launchFragment()
            // system automatically recreate fragment when activity was recreated
            // we need to create fragment manually only if it is activity's first creation
            // or we will get two duplicate fragments
        }
    }

    private fun launchFragment() {
        val fragment = ShopItemFragment
            .newInstance(
                intent.getIntExtra(
                    ITEM_ID_EXTRA,
                    ITEM_NOT_FOUND_VAL
                )
            )
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerShopItem, fragment)
            .commit()
    }

    override fun onEditingFinished() {
        finish()
    }

    companion object {

        const val ITEM_ID_EXTRA = "itemId"
        const val ITEM_NOT_FOUND_VAL = -1

        fun newIntent(context: Context, itemId: Int?): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(ITEM_ID_EXTRA, itemId)
            return intent
        }
    }
}