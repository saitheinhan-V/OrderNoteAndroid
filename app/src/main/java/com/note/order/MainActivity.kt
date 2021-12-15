package com.note.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adView.adSize = AdSize.BANNER

       // adView.adUnitId = "ca-app-pub-3940256099942544/3347511713"

//        MobileAds.initialize(this) {}
//
//        val adRequest = AdRequest.Builder().build()
//        adRequest.isTestDevice(applicationContext)
//        adView.loadAd(adRequest)


        val mainFragment = mainFragment as NavHostFragment
        navController = mainFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)


        bottomNavigationHome.setOnNavigationItemSelectedListener { menuItem ->
            val destination = when (menuItem.itemId){
                R.id.menuHome -> R.id.homeFragment
                R.id.menuCustomer -> R.id.customerListFragment
                R.id.menuPayment -> R.id.paymentCheckFragment
                R.id.menuCash -> R.id.cashBookFragment
                R.id.menuSetup -> R.id.setupFragment
                else -> R.id.homeFragment
            }

            navGraph.startDestination = destination
            navController.graph = navGraph

        return@setOnNavigationItemSelectedListener true
        }


    }
}