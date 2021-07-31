package com.bypavelshell.mathcreater.currencyconverter.data

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser


class SimpleParser {
    fun parser(stringJson: String): CurrencyRateModel? {


        val root: JSONObject = JSONParser().parse(stringJson) as JSONObject

        try {
            val jsonCurrency = root["Valute"] as JSONObject
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

            return CurrencyRateModel(
                date = root["Date"] as String,
                previousDate = root["PreviousDate"] as String,
                currencies = listCurrency
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}