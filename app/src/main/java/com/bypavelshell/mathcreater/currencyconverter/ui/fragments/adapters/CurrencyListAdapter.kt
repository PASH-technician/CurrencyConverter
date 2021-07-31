package com.bypavelshell.mathcreater.currencyconverter.ui.fragments.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bypavelshell.mathcreater.currencyconverter.R
import com.bypavelshell.mathcreater.currencyconverter.data.Currency
import kotlinx.android.synthetic.main.item_currency.view.*

class CurrencyListAdapter(
    currencyList: MutableList<Currency>,
) : RecyclerView.Adapter<CurrencyListAdapter.ItemCurrency>() {

    var currencyList = currencyList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCurrency {
        val context = parent.context
        val layoutIdForListItem = R.layout.item_currency
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListItem, parent, false)
        return ItemCurrency(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemCurrency, position: Int) {
        holder.charCode.text = currencyList[position].charCode
        holder.name.text = currencyList[position].name
        val currencyToDay = currencyList[position].value
        holder.valueToDay.text = currencyToDay.toString()
        val currencyYesterday = currencyList[position].previous
        holder.valueYesterday.text = currencyYesterday.toString()

        when {
            currencyToDay!! > currencyYesterday!! -> {
                holder.changingCurrency.visibility = View.VISIBLE
                holder.changingCurrencyValue.visibility = View.VISIBLE
                holder.changingCurrency.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
                holder.changingCurrencyValue.text = "+${currencyToDay - currencyYesterday}"
                holder.changingCurrencyValue.setTextColor(Color.parseColor(R.color.exchange_rate_up.toString()))
            }
            currencyToDay < currencyYesterday -> {
                holder.changingCurrency.visibility = View.VISIBLE
                holder.changingCurrencyValue.visibility = View.VISIBLE
                holder.changingCurrency.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                holder.changingCurrencyValue.text = (currencyToDay - currencyYesterday).toString()
                holder.changingCurrencyValue.setTextColor(Color.parseColor(R.color.exchange_rate_drop.toString()))
            }
            else -> {
                holder.changingCurrency.visibility = View.INVISIBLE
                holder.changingCurrencyValue.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    class ItemCurrency(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.name
        var charCode: TextView = itemView.charCode
        var valueToDay: TextView = itemView.valueToDay
        var valueYesterday: TextView = itemView.valueYesterday
        var changingCurrency: ImageView = itemView.changingCurrency
        var changingCurrencyValue: TextView = itemView.changingCurrencyValue
    }
}

private fun String.replacementWithDot(): String {
    var result = ""
    for ((index, char) in this.withIndex()) {
        result += if (char == ',') {
            '.'
        } else {
            char
        }
    }
    return result
}
