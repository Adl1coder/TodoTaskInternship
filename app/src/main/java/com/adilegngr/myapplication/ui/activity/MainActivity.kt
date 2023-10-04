package com.adilegngr.myapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.adilegngr.myapplication.R
import com.adilegngr.myapplication.databinding.ActivityMainBinding
import com.adilegngr.myapplication.util.gone
import com.adilegngr.myapplication.util.visible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView,navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.splashScreen -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                    binding.bottomNavView.gone()
                }
                else -> {
                    window.statusBarColor = ContextCompat.getColor(this,R.color.main_color)
                    binding.bottomNavView.visible()
                }
            }
        }
    }
}