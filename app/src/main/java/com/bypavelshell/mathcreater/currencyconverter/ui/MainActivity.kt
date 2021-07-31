package com.bypavelshell.mathcreater.currencyconverter.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bypavelshell.mathcreater.currencyconverter.R
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.CurrencyConverterFragment
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.CurrencyFragment
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {

    private val DATE_UPDATE_INFO: Calendar = Calendar.getInstance()
    private val INTERNET_CHENG = "com.bypavelshell.checkdate.MIDNIGHT_HAS_COME"

    private lateinit var checkChangeInternetConnect: BroadcastReceiver
    private lateinit var dateChangeReceiver: BroadcastReceiver

    private var toDayDate = Calendar.getInstance()
    private var toYesterday = Calendar.getInstance()
    private val currencyFragment = CurrencyFragment()
    private val currencyConverterFragment = CurrencyConverterFragment()
    private var url: URL? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DATE_UPDATE_INFO.set(Calendar.HOUR_OF_DAY, 24)
        DATE_UPDATE_INFO.set(Calendar.MINUTE, 0)
        DATE_UPDATE_INFO.set(Calendar.SECOND, 0)
        DATE_UPDATE_INFO.set(Calendar.MILLISECOND, 0)

        toYesterday.add(Calendar.DATE, -1)

        setAlarmAtNextMidnight(this)

        val internetConnectFilter = IntentFilter()
        internetConnectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        checkChangeInternetConnect = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val internet = cm.activeNetworkInfo
                if (internet != null && internet.isConnected) {
                    cannotAccessTheInternet.visibility = View.INVISIBLE
                }else{
                    cannotAccessTheInternet.visibility = View.VISIBLE
                }
            }
        }

        val dateChangeFilter = IntentFilter()
        dateChangeFilter.addAction(INTERNET_CHENG)
        dateChangeReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action.equals(INTERNET_CHENG))
                    Log.d("TAG","Вот и наступило завтра")

                Toast.makeText(context, "dateChangeReceiver", Toast.LENGTH_LONG).show()

                if (Calendar.getInstance().time.minutes == DATE_UPDATE_INFO.time.minutes) {
                    Toast.makeText(context, "сменилась дата", Toast.LENGTH_LONG).show()
                }
            }
        }

        registerReceiver(dateChangeReceiver, dateChangeFilter)
        registerReceiver(checkChangeInternetConnect, internetConnectFilter)
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(currencyFragment, "Курсы валют")

        adapter.addFragment(currencyConverterFragment, "Конвертер")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_attach_money_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_compare_arrows_24)
    }

    private fun setAlarmAtNextMidnight(appContext: Context) {
        val alarmManager = appContext.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(INTERNET_CHENG)
        val pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent, 0)
        alarmManager[AlarmManager.RTC_WAKEUP, DATE_UPDATE_INFO.timeInMillis] = pendingIntent
    }
}