package com.bypavelshell.mathcreater.currencyconverter.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bypavelshell.mathcreater.currencyconverter.R
import com.bypavelshell.mathcreater.currencyconverter.data.Currency
import com.bypavelshell.mathcreater.currencyconverter.data.CurrencyModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_currency_converter.view.*

class CurrencyConverterFragment : Fragment() {

    private lateinit var choosingFirstCurrency: Spinner
    private lateinit var firstCurrencyInput: TextInputEditText
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var choosingSecondCurrency: Spinner
    private lateinit var secondCurrencyInput: TextInputEditText
    private lateinit var textInputLayout2: TextInputLayout
    private lateinit var choosingThirdCurrency: Spinner
    private lateinit var thirdCurrencyInput: TextInputEditText
    private lateinit var textInputLayout3: TextInputLayout

    val liveData = MutableLiveData<CurrencyModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currency_converter, container, false)

        choosingFirstCurrency = view.choosingFirstCurrency
        firstCurrencyInput = view.firstCurrencyInput
        textInputLayout = view.textInputLayout

        choosingSecondCurrency = view.choosingSecondCurrency
        secondCurrencyInput = view.seconCurrencyInput
        textInputLayout2 = view.textInputLayout2

        choosingThirdCurrency = view.choosingThirdCurrency
        thirdCurrencyInput = view.thirdCurrencyInput
        textInputLayout3 = view.textInputLayout3

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        liveData.observe(this.viewLifecycleOwner) {
            choosingFirstCurrency.adapter = this.context?.let { context ->
                ArrayAdapter(context,
                    R.layout.dropdown_item,
                    creatingListCurrencies(it.currencies)
                )
            }
            choosingSecondCurrency.adapter = this.context?.let { context ->
                ArrayAdapter(
                    context, R.layout.dropdown_item,
                    creatingListCurrencies(it.currencies)
                )
            }
            choosingThirdCurrency.adapter = this.context?.let { context ->
                ArrayAdapter(
                    context, R.layout.dropdown_item,
                    creatingListCurrencies(it.currencies)
                )
            }

            choosingFirstCurrency.onItemSelectedListener =
                selectedListener(it.currencies, textInputLayout)

            choosingSecondCurrency.onItemSelectedListener =
                selectedListener(it.currencies, textInputLayout2)

            choosingThirdCurrency.onItemSelectedListener =
                selectedListener(it.currencies, textInputLayout3)

            firstCurrencyInput.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    
                }

                override fun afterTextChanged(s: Editable?) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun selectedListener(
        currencies: MutableList<Currency>,
        textInputLayout: TextInputLayout,
    ): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                textInputLayout.hint = currencies[position].name
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun creatingListCurrencies(currencies: MutableList<Currency>): MutableList<String> {
        val resultList = mutableListOf<String>()
        currencies.forEach {
            resultList.add(it.toString())
        }
        return resultList
    }
}