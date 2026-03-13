package com.tengiz.itstepacademy_finalproject_android.ui.activity

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.tengiz.itstepacademy_finalproject_android.R
import com.tengiz.itstepacademy_finalproject_android.databinding.ActivityAdminAppBinding
import com.tengiz.itstepacademy_finalproject_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAppBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate view binding
        binding = ActivityAdminAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}