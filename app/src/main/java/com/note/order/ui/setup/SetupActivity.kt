package com.note.order.ui.setup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.note.order.R
import kotlinx.android.synthetic.main.activity_setup.*

class SetupActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        val hostFragment = hostFragment as NavHostFragment
        navController = hostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.nav_setup)


        id = intent.getIntExtra("id",0)

        val destination = when (id){
            0 -> {
                R.id.colorFragment
            }
            6 -> {
                R.id.brandFragment
            }
            2 -> {
                R.id.colorFragment
            }
            else -> {
                R.id.colorFragment
            }
        }

        graph.startDestination = destination
        navController.graph = graph

    }
}