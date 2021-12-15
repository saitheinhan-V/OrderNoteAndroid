package com.note.order.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.note.order.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragment = accountHostFragment as NavHostFragment
        navController = fragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_account)

        val destination = R.id.loginFragment

        navGraph.startDestination = destination
        navController.graph = navGraph
    }
}