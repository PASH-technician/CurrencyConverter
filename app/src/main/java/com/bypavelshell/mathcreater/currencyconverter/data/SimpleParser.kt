package com.bypavelshell.mathcreater.currencyconverter.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.URL


class SimpleParser {
    fun parser(url: URL) = CoroutineScope(Dispatchers.Default).async {
            val root: JSONObject = JSONParser().parse(url.readText()) as JSONObject

            try {
                val jsonCurrency = root!!["Valute"] as JSONObject
                val arrayKeys = jsonCurrency.keys
                val listCurrency = mutableListOf<Currency>()
                arrayKeys.forEach { key ->
                    val jsonItem = jsonCurrency[key] as JSONObject
                    listCurrency.add(
                        Currency(
                            id = jsonItem["ID"] as String,
                            numCode = jsonItem["NumCode"] as String,
                            charCode = jsonItem["CharCode"] as String,
                            nominal = jsonItem["Nominal"] as Long,
                            name = jsonItem["Name"] as String,
                            value = jsonItem["Value"] as Double,
                            previous = jsonItem["Previous"] as Double
                        )
                    )
                }

                CurrencyRateModel(
                    date = root["Date"] as String,
                    previousDate = root["PreviousDate"] as String,
                    currencies = listCurrency
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
}