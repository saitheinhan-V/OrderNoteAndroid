package com.note.order.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.note.order.R
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var route = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        route = intent.getIntExtra("route",1)
        //1-add //2-detail

        val navHostFragment = orderNavHostFragment as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_order)

        val destination = when(route){
            1 -> {
                R.id.addOrderFragment
            }
            2 -> {
                R.id.orderDetailFragment
            }
            else -> {
                R.id.addOrderFragment
            }
        }

        navGraph.startDestination = destination
        navController.graph = navGraph

    }
}