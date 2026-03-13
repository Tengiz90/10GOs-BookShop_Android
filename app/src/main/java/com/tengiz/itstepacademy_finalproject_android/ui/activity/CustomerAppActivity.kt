package com.tengiz.itstepacademy_finalproject_android.ui.activity

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tengiz.itstepacademy_finalproject_android.R
import com.tengiz.itstepacademy_finalproject_android.databinding.ActivityCustomerAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerAppBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerAppBinding.inflate(layoutInflater)
        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentContainerView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply padding: top for status bar, bottom for nav bar if needed
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, v.paddingBottom)

            insets
        }
        setContentView(binding.root)


        setup()
    }

    private fun setup() {
        setupBottomNavigation()
        setupBackPressed()
    }

    private fun setupBackPressed() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as androidx.navigation.fragment.NavHostFragment
        navController = navHostFragment.navController

        // 3. Setup back button logic
        onBackPressedDispatcher.addCallback(this, true) {
            if (!navController.popBackStack()) {
                finish()
            }
        }

    }

    private fun setupBottomNavigation() {
        bottomNavigationView = binding.bottomNavigationView

        navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        bottomNavigationView.setOnItemSelectedListener {
            if(!it.isChecked) {
                when (it.itemId) {

                    R.id.homeFragment -> {
                        navController.popBackStack(navController.currentDestination!!.id, true)
                        navController.navigate(R.id.homeFragment)
                        true
                    }

                    R.id.categoryFragment -> {
                        navController.popBackStack(navController.currentDestination!!.id, true)
                        navController.navigate(R.id.categoryFragment)
                        true
                    }


                    R.id.userFragment -> {
                        navController.popBackStack(navController.currentDestination!!.id, true)
                        navController.navigate(R.id.userFragment)
                        true
                    }

                    else -> false
                }
            }
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}