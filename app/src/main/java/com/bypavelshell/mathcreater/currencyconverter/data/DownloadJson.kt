package com.bypavelshell.mathcreater.currencyconverter.data

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL

class DownloadJson : AsyncTask<String, Unit, CurrencyModel>() {
    override fun doInBackground(vararg params: String?): CurrencyModel? {
        val url = URL(params[0])
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val text = urlConnection.inputStream.bufferedReader().readText()

            return SimpleParser().parser(text)

        } finally {
            urlConnection.disconnect()
        }
    }
}