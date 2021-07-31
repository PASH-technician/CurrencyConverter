package com.bypavelshell.mathcreater.currencyconverter.data

data class CurrencyRateModel(
    var date: String?,
    var previousDate: String?,
    var currencies: MutableList<Currency>
)