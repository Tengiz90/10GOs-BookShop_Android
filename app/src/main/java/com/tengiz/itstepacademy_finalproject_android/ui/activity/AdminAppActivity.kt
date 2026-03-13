package com.tengiz.itstepacademy_finalproject_android.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tengiz.itstepacademy_finalproject_android.R
import com.tengiz.itstepacademy_finalproject_android.databinding.ActivityAdminAppBinding
import com.tengiz.itstepacademy_finalproject_android.databinding.ActivityMainBinding

class AdminAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAppBinding

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
}