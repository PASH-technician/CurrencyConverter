package com.bypavelshell.mathcreater.currencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bypavelshell.mathcreater.currencyconverter.fragments.CurrencyConverterFragment
import com.bypavelshell.mathcreater.currencyconverter.fragments.CurrencyFragment
import com.bypavelshell.mathcreater.currencyconverter.fragments.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTabs()
    }

    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CurrencyFragment(), "Курсы валют")
        adapter.addFragment(CurrencyConverterFragment(), "Конвертер")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_attach_money_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_compare_arrows_24)
    }
}