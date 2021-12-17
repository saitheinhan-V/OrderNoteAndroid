package com.note.order.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.note.order.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private var route = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        route = intent.getIntExtra("route",1)

        val navHostFragment = settingNavHostFragment as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_setting)

        val destination = when(route){
            1 -> {
                R.id.passwordFragment
            }
            2 -> {
                R.id.passwordFragment
            }
            else -> {
                R.id.passwordFragment
            }
        }

        navGraph.startDestination = destination
        navController.graph = navGraph
    }
}