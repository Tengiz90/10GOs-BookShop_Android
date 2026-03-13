package com.tengiz.itstepacademy_finalproject_android.ui.element.default_app

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.tengiz.itstepacademy_finalproject_android.data.local.SecureStorage
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentDefaultAppHomeBinding
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment

import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject
import android.util.Base64
import com.tengiz.itstepacademy_finalproject_android.ui.activity.AdminAppActivity
import com.tengiz.itstepacademy_finalproject_android.ui.activity.CustomerAppActivity

@AndroidEntryPoint
class DefaultAppHomeFragment : BaseFragment<FragmentDefaultAppHomeBinding>(FragmentDefaultAppHomeBinding::inflate) {

    @Inject
    lateinit var secureStorage: SecureStorage

    override fun setup() {
        // Run the session check as soon as the fragment is ready
        checkSession()
    }

    private fun checkSession() {
        val token = secureStorage.getToken()

        // Check if token exists and isn't expired
        if (token != null && !isTokenExpired(token)) {
            val role = getRoleFromToken(token)

            when (role) {
                "Admin" -> {
                    val intent = Intent(requireContext(), AdminAppActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish() // Close current activity so user can't "back" into login
                }
                "Customer" -> {
                    val intent = Intent(requireContext(), CustomerAppActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun getRoleFromToken(token: String): String {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return ""

            // Decode the Payload (second part of the JWT)
            val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
            val jsonObject = JSONObject(payload)

            // Try standard "role" first, then the common ASP.NET URI claim
            var role = jsonObject.optString("role", "")
            if (role.isEmpty()) {
                role = jsonObject.optString("http://schemas.microsoft.com/ws/2008/06/identity/claims/role", "")
            }
            role
        } catch (e: Exception) {
            ""
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return true

            val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
            val jsonObject = JSONObject(payload)

            // 'exp' is in seconds, convert to milliseconds
            val expirationTime = jsonObject.getLong("exp") * 1000
            System.currentTimeMillis() > expirationTime
        } catch (e: Exception) {
            true // If parsing fails, assume it's expired
        }
    }

    override fun listeners() {
        super.listeners()

        binding.registerBtn.setOnClickListener {
            val action = DefaultAppHomeFragmentDirections.actionDefaultAppHomeFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        binding.signBtn.setOnClickListener {
            val action = DefaultAppHomeFragmentDirections.actionDefaultAppHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}