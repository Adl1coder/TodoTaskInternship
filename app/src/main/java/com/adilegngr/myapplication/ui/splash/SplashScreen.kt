package com.adilegngr.myapplication.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.adilegngr.myapplication.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.getMainLooper()).postDelayed({
            val action = SplashScreenDirections.actionSplashScreenToNotesScreen()
            findNavController().navigate(action)
        },3000)
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}