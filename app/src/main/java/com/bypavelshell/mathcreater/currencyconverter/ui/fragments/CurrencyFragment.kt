package com.bypavelshell.mathcreater.currencyconverter.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bypavelshell.mathcreater.currencyconverter.R
import com.bypavelshell.mathcreater.currencyconverter.data.CurrencyRateModel
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.adapters.CurrencyListAdapter
import kotlinx.android.synthetic.main.fragment_currency.*




class CurrencyFragment : Fragment() {

    private var adapter: CurrencyListAdapter? = null
    var currencies: CurrencyRateModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun loadInfoCurrencyList(currencies: CurrencyRateModel) {
        currencyList.setOnRefreshListener {
            adapter!!.currencyList = currencies.currencies
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun checkInternetConnect(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) return true

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) return true
        wifiInfo = cm.activeNetworkInfo
        if (wifiInfo != null && wifiInfo.isConnected) return true

        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }
}