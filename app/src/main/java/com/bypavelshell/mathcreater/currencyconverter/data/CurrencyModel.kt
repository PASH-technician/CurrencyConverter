package com.bypavelshell.mathcreater.currencyconverter.data

data class CurrencyModel(
    var date: String?,
    var previousDate: String?,
    var currencies: MutableList<Currency>
)