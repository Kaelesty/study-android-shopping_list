package com.kaelesty.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView.OnEditorActionListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kaelesty.shoppinglist.R

class ShopItemFragment: Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tietName: TextInputEditText
    private lateinit var tilQuantity: TextInputLayout
    private lateinit var tietQuantity: TextInputEditText
    private lateinit var buttonSave: Button

    private var itemId: Int = ITEM_NOT_FOUND_VAL

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = requireArguments().getInt(ITEM_ID_EXTRA, ITEM_NOT_FOUND_VAL)
        // Parameters are parsed in OnCreate and not in OnViewCreated,
        // so that if the necessary parameters are missing, throw an exception
        // before the View is created and not waste time on this
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditorActionListener) {
            onEditingFinishedListener = context as OnEditingFinishedListener
        }
        else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
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

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.tilName)
        tilQuantity = view.findViewById(R.id.tilQuanity)

        tietName = view.findViewById(R.id.tietName)
        tietName.doOnTextChanged { _, _, _, _ ->  tilName.error = "" }

        tietQuantity = view.findViewById(R.id.tietQuanity)
        tietQuantity.doOnTextChanged { _, _, _, _ -> tilQuantity.error = "" }

        buttonSave = view.findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            viewModel.save(tietName.text.toString(), tietQuantity.text.toString())
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
            quantityToShow.observe(viewLifecycleOwner) {
                tietQuantity.setText(it)
            }
            nameError.observe(viewLifecycleOwner) {
                if (it) {
                    tilName.error = NAME_ERROR_MESSAGE
                }
                else {
                    tilName.error = ""
                }
            }
            quantityError.observe(viewLifecycleOwner) {
                if (it) {
                    tilQuantity.error = QUANTITY_ERROR_MESSAGE
                }
                else {
                    tilQuantity.error = ""
                }
            }
            shouldFinish.observe(viewLifecycleOwner) {
                onEditingFinishedListener.onEditingFinished()
            }
        }
    }

    companion object {

        const val ITEM_ID_EXTRA = "itemId"
        const val ITEM_NOT_FOUND_VAL = -1

        const val NAME_ERROR_MESSAGE = "Wrong name"
        const val QUANTITY_ERROR_MESSAGE = "Wrong quantity"

        fun newInstance(itemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply { putInt(ITEM_ID_EXTRA, itemId) }
//                Passing parameters to the fragment occurs through the Bundle
//                and not through the constructor,
//                because the parameters passed through the constructor will be lost
//                when the fragment is recreated (for example, when the screen is flipped)
            }
        }

        interface OnEditingFinishedListener {
            fun onEditingFinished()
        }
    }
}