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
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.bypavelshell.mathcreater.currencyconverter.R
import com.bypavelshell.mathcreater.currencyconverter.data.CurrencyModel
import com.bypavelshell.mathcreater.currencyconverter.data.DownloadJson
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.CurrencyConverterFragment
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.CurrencyFragment
import com.bypavelshell.mathcreater.currencyconverter.ui.fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val DATE_UPDATE_INFO: Calendar = Calendar.getInstance()
    private val INTERNET_CHENG = "com.bypavelshell.checkdate.MIDNIGHT_HAS_COME"

    private var checkInternetConnect = false
    private var checkNewInfoCurrencies = true

    private lateinit var checkChangeInternetConnect: BroadcastReceiver
    private lateinit var dateChangeReceiver: BroadcastReceiver

    private var toDayDate = Calendar.getInstance()
    private var toYesterday = Calendar.getInstance()

    private lateinit var liveDataCurrencyFragment: MutableLiveData<CurrencyModel>
    private lateinit var liveDataCurrencyConverterFragment: MutableLiveData<CurrencyModel>

    private var url: URL? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DATE_UPDATE_INFO.set(Calendar.HOUR_OF_DAY, 24)
        DATE_UPDATE_INFO.set(Calendar.MINUTE, 0)
        DATE_UPDATE_INFO.set(Calendar.SECOND, 0)
        DATE_UPDATE_INFO.set(Calendar.MILLISECOND, 0)

        toYesterday.add(Calendar.DATE, -1)

        setUpTabs()

        setAlarmAtNextMidnight(this)

        //Обновление состояния подключения к интернету
        val internetConnectFilter = IntentFilter()
        internetConnectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        checkChangeInternetConnect = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val internet = cm.activeNetworkInfo
                if (internet != null && internet.isConnected) {
                    checkInternetConnect = true
                    cannotAccessTheInternet.visibility = View.INVISIBLE
                    if (checkInternetConnect && checkNewInfoCurrencies){
                        updateInfoCurrencies()
                    }
                }else{
                    checkInternetConnect = false
                    cannotAccessTheInternet.visibility = View.VISIBLE
                }
            }
        }

        //Обновление текущей даты
        val dateChangeFilter = IntentFilter()
        dateChangeFilter.addAction(INTERNET_CHENG)
        dateChangeReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action.equals(INTERNET_CHENG))
                    Log.d("TAG","Вот и наступило завтра")

                Toast.makeText(context, "dateChangeReceiver", Toast.LENGTH_LONG).show()

                if (Calendar.getInstance().time.minutes == DATE_UPDATE_INFO.time.minutes) {
                    Toast.makeText(context, "сменилась дата", Toast.LENGTH_LONG).show()
                    checkNewInfoCurrencies = true

                    if (checkInternetConnect && checkNewInfoCurrencies){
                        updateInfoCurrencies()
                    }
                }
            }
        }

        registerReceiver(dateChangeReceiver, dateChangeFilter)
        registerReceiver(checkChangeInternetConnect, internetConnectFilter)
    }


    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        val currencyFragment = CurrencyFragment()
        liveDataCurrencyFragment = currencyFragment.liveData
        adapter.addFragment(currencyFragment, "Курсы валют")

        val currencyConverterFragment = CurrencyConverterFragment()
        liveDataCurrencyConverterFragment = currencyConverterFragment.liveData
        adapter.addFragment(currencyConverterFragment, "Конвертер")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_attach_money_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_compare_arrows_24)

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(tabs.windowToken, 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setAlarmAtNextMidnight(appContext: Context) {
        val alarmManager = appContext.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(INTERNET_CHENG)
        val pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent, 0)
        alarmManager[AlarmManager.RTC_WAKEUP, DATE_UPDATE_INFO.timeInMillis] = pendingIntent
    }

    private fun updateInfoCurrencies() {
        val data = SimpleDateFormat("yyyy//MM//dd", Locale.getDefault())
            .format(toYesterday.time)

        val currencyRateModel = DownloadJson()
            .execute("https://www.cbr-xml-daily.ru//daily_json.js")
            .get()


        if (::liveDataCurrencyFragment.isInitialized){
            liveDataCurrencyFragment.value = currencyRateModel

            checkNewInfoCurrencies = false
        }

        if (::liveDataCurrencyConverterFragment.isInitialized){
            liveDataCurrencyConverterFragment.value = currencyRateModel
            checkNewInfoCurrencies = false
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dateChangeReceiver)
        unregisterReceiver(checkChangeInternetConnect)
    }
}