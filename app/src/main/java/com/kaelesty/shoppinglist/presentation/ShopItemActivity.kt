package com.kaelesty.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kaelesty.shoppinglist.R

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tietName: TextInputEditText
    private lateinit var tilQuanity: TextInputLayout
    private lateinit var tietQuanity: TextInputEditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        initViews()
        initViewModel()
    }

    fun initViews() {
        tilName = findViewById(R.id.tilName)
        tilQuanity = findViewById(R.id.tilQuanity)

        tietName = findViewById(R.id.tietName)
        tietName.doOnTextChanged { text, start, before, count ->  tilName.error = "" }

        tietQuanity = findViewById(R.id.tietQuanity)
        tietQuanity.doOnTextChanged { text, start, before, count -> tilQuanity.error = "" }

        buttonSave = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            viewModel.save(tietName.text.toString(), tietQuanity.text.toString())
        }
    }

    private fun initViewModel() {
        viewModel = ShopItemVMFactory(
            application,
            intent.getIntExtra(ITEM_ID_EXTRA, ITEM_NOT_FOUND_VAL)
        ).create(ShopItemViewModel::class.java)

        with(viewModel) {
            nameToShow.observe(this@ShopItemActivity) {
                tietName.setText(it)
            }
            quanityToShow.observe(this@ShopItemActivity) {
                tietQuanity.setText(it)
            }
            nameError.observe(this@ShopItemActivity) {
                if (it) {
                    tilName.error = NAME_ERROR_MESSAGE
                }
                else {
                    tilName.error = ""
                }
            }
            quanityError.observe(this@ShopItemActivity) {
                if (it) {
                    tilQuanity.error = QUANITY_ERROR_MESSAGE
                }
                else {
                    tilQuanity.error = ""
                }
            }
            shouldFinish.observe(this@ShopItemActivity) {
                finish()
            }
        }
    }

    companion object {

        const val ITEM_ID_EXTRA = "itemId"
        const val ITEM_NOT_FOUND_VAL = -1

        const val NAME_ERROR_MESSAGE = "Wrong name"
        const val QUANITY_ERROR_MESSAGE = "Wrong quanity"
        fun newIntent(context: Context, itemId: Int?): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(ITEM_ID_EXTRA, itemId)
            return intent
        }
    }
}