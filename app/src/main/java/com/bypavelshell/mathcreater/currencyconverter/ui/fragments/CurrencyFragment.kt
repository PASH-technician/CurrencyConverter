package com.bypavelshell.mathcreater.currencyconverter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bypavelshell.mathcreater.currencyconverter.R
import com.bypavelshell.mathcreater.currencyconverter.data.CurrencyModel
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.adapters.CurrencyListAdapter
import kotlinx.android.synthetic.main.fragment_currency.view.*


class CurrencyFragment : Fragment() {

    private lateinit var loadingCurrencyInfo: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var toDayDate: TextView
    private lateinit var toYesterdayDate: TextView

    val liveData = MutableLiveData<CurrencyModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currency, container, false)

        loadingCurrencyInfo = view.loadingCurrencyInfo
        recyclerView = view.recyclerView
        toDayDate = view.toDayDate
        toYesterdayDate = view.toYesterdayDate

        recyclerView.visibility = View.INVISIBLE
        loadingCurrencyInfo.visibility = View.VISIBLE

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        liveData.observe(this.viewLifecycleOwner){
            val adapter = CurrencyListAdapter(it)
            recyclerView.adapter = adapter
            recyclerView.visibility = View.VISIBLE
            loadingCurrencyInfo.visibility = View.INVISIBLE
            toDayDate.text = formatDateString(it.previousDate!!.subSequence(0..9))
            toDayDate.visibility = View.VISIBLE
            toYesterdayDate.text = formatDateString(it.date!!.subSequence(0..9))
            toYesterdayDate.visibility = View.VISIBLE
        }
    }

    private fun formatDateString(date: CharSequence): String{
        val resultString = date.split("-")
        return "${resultString[2]}.${resultString[1]}.${resultString[0]}"
    }
}