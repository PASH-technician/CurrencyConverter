package com.bypavelshell.mathcreater.currencyconverter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.bypavelshell.mathcreater.currencyconverter.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_currency_converter.view.*

class CurrencyConverterFragment : Fragment() {

    private lateinit var choosingFirstCurrency: Spinner
    private lateinit var firstCurrencyInput: TextInputEditText
    private lateinit var choosingSecondCurrency: Spinner
    private lateinit var secondCurrencyInput: TextInputEditText
    private lateinit var choosingThirdCurrency: Spinner
    private lateinit var thirdCurrencyInput: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currency_converter, container, false)

        choosingFirstCurrency = view.choosingFirstCurrency
        firstCurrencyInput = view.firstCurrencyInput
        choosingSecondCurrency = view.choosingSecondCurrency
        secondCurrencyInput = view.seconCurrencyInput
        choosingThirdCurrency = view.choosingThirdCurrency
        thirdCurrencyInput = view.thirdCurrencyInput

        return view
    }
}