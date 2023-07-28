package com.kaelesty.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kaelesty.shoppinglist.R

class ShopItemFragment: Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tietName: TextInputEditText
    private lateinit var tilQuanity: TextInputLayout
    private lateinit var tietQuanity: TextInputEditText
    private lateinit var buttonSave: Button

    private var itemId: Int = ITEM_NOT_FOUND_VAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = requireArguments().getInt(ITEM_ID_EXTRA, ITEM_NOT_FOUND_VAL)
        // Parameters are parsed in OnCreate and not in OnViewCreated,
        // so that if the necessary parameters are missing, throw an exception
        // before the View is created and not waste time on this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initViewModel()
    }

    fun initViews(view: View) {
        tilName = view.findViewById(R.id.tilName)
        tilQuanity = view.findViewById(R.id.tilQuanity)

        tietName = view.findViewById(R.id.tietName)
        tietName.doOnTextChanged { text, start, before, count ->  tilName.error = "" }

        tietQuanity = view.findViewById(R.id.tietQuanity)
        tietQuanity.doOnTextChanged { text, start, before, count -> tilQuanity.error = "" }

        buttonSave = view.findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            viewModel.save(tietName.text.toString(), tietQuanity.text.toString())
        }
    }

    private fun initViewModel() {
        viewModel = ShopItemVMFactory(
            requireActivity().application,
            itemId
        ).create(ShopItemViewModel::class.java)

        with(viewModel) {
            nameToShow.observe(viewLifecycleOwner) {
                tietName.setText(it)
            }
            quanityToShow.observe(viewLifecycleOwner) {
                tietQuanity.setText(it)
            }
            nameError.observe(viewLifecycleOwner) {
                if (it) {
                    tilName.error = NAME_ERROR_MESSAGE
                }
                else {
                    tilName.error = ""
                }
            }
            quanityError.observe(viewLifecycleOwner) {
                if (it) {
                    tilQuanity.error = QUANITY_ERROR_MESSAGE
                }
                else {
                    tilQuanity.error = ""
                }
            }
            shouldFinish.observe(viewLifecycleOwner) {
                if (activity is ShopItemActivity) {
                    activity?.onBackPressed()
                }
                else {
                    activity?.run {
                        supportFragmentManager.beginTransaction().remove(this@ShopItemFragment)
                            .commitAllowingStateLoss()
                    }
                }
            }
        }
    }

    companion object {

        const val ITEM_ID_EXTRA = "itemId"
        const val ITEM_NOT_FOUND_VAL = -1

        const val NAME_ERROR_MESSAGE = "Wrong name"
        const val QUANITY_ERROR_MESSAGE = "Wrong quanity"

        fun newInstance(itemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply { putInt(ITEM_ID_EXTRA, itemId) }
//                Passing parameters to the fragment occurs through the Bundle
//                and not through the constructor,
//                because the parameters passed through the constructor will be lost
//                when the fragment is recreated (for example, when the screen is flipped)
            }
        }
    }
}