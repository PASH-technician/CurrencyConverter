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
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyConverterFragment : Fragment() {

    private lateinit var choosingFirstCurrency: Spinner
    private lateinit var firstCurrencyInput: TextInputEditText
    private lateinit var textInputLayoutFirst: TextInputLayout
    private lateinit var choosingSecondCurrency: Spinner
    private lateinit var secondCurrencyInput: TextInputEditText
    private lateinit var textInputLayoutSecond: TextInputLayout

    val liveData = MutableLiveData<CurrencyModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currency_converter, container, false)

        choosingFirstCurrency = view.choosingFirstCurrency
        firstCurrencyInput = view.firstCurrencyInput
        textInputLayoutFirst = view.textInputLayout

        choosingSecondCurrency = view.choosingSecondCurrency
        secondCurrencyInput = view.seconCurrencyInput
        textInputLayoutSecond = view.textInputLayout2

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

            choosingFirstCurrency.onItemSelectedListener =
                selectedListener(it.currencies, textInputLayoutFirst)

            choosingSecondCurrency.onItemSelectedListener =
                selectedListener(it.currencies, textInputLayoutSecond)


            firstCurrencyInput.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int, ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(firstCurrencyInput.hasFocus())
                        firstTextUpdate(s, it.currencies)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            secondCurrencyInput.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int, ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(secondCurrencyInput.hasFocus())
                        secondTextUpdate(s, it.currencies)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            firstCurrencyInput.onFocusChangeListener = changeFocusListener()
            secondCurrencyInput.onFocusChangeListener = changeFocusListener()
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
                if(firstCurrencyInput.hasFocus()){
                    firstTextUpdate(firstCurrencyInput.text, currencies)
                }else if(secondCurrencyInput.hasFocus()){
                    secondTextUpdate(secondCurrencyInput.text, currencies)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun changeFocusListener(): View.OnFocusChangeListener{
        return View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                (v as TextInputEditText).setText("1")
            }
        }
    }

    private fun firstTextUpdate(firstText: CharSequence?, currencies: MutableList<Currency>){
        if (firstText.toString() != ""){
            val firstCurrency = currencies[choosingFirstCurrency.selectedItemPosition]
                .value

            val secondCurrency = currencies[choosingSecondCurrency.selectedItemPosition]
                .value

            val value = BigDecimal.valueOf(firstCurrency!! * firstText.toString()
                .toDouble() / secondCurrency!!).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros()
            secondCurrencyInput
                .setText(value.toString())

        }else if(firstCurrencyInput.focusable == View.FOCUSABLE){
            secondCurrencyInput.setText("")
        }
    }

    private fun secondTextUpdate(firstText: CharSequence?, currencies: MutableList<Currency>){
        if (firstText.toString() != "" && secondCurrencyInput.focusable == View.FOCUSABLE){
            val secondCurrency = currencies[choosingSecondCurrency.selectedItemPosition]
                .value

            val firstCurrency = currencies[choosingFirstCurrency.selectedItemPosition]
                .value

            val value = BigDecimal.valueOf(secondCurrency!! * firstText.toString()
                .toDouble() / firstCurrency!!).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros()

            firstCurrencyInput
                .setText(value.toString())

        }else if (secondCurrencyInput.focusable == View.FOCUSABLE){
            firstCurrencyInput.setText("")
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