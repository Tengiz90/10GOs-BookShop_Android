package com.tengiz.itstepacademy_finalproject_android.ui.element.default_app

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tengiz.itstepacademy_finalproject_android.data.local.SecureStorage
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentLoginBinding
import com.tengiz.itstepacademy_finalproject_android.extensions.toRoleString
import com.tengiz.itstepacademy_finalproject_android.model.GetUserByEmailAndPasswordResult
import com.tengiz.itstepacademy_finalproject_android.ui.activity.AdminAppActivity
import com.tengiz.itstepacademy_finalproject_android.ui.activity.CustomerAppActivity
import com.tengiz.itstepacademy_finalproject_android.ui.activity.MainActivity
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment
import com.tengiz.itstepacademy_finalproject_android.ui.viewmodel.LoginViewModel
import com.tengiz.itstepacademy_finalproject_android.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var secureStorage: SecureStorage

    override fun setup() {
        observeLoginState()
    }

    override fun listeners() {
        super.listeners()

        binding.signInBtn.setOnClickListener {
            // Reset errors and remove reserved spaces
            binding.emailLayout.error = null
            binding.emailLayout.isErrorEnabled = false
            binding.passwordLayout.error = null
            binding.passwordLayout.isErrorEnabled = false

            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()
            val client = 0 // Android client

            var hasError = false

            if (email.isEmpty()) {
                binding.emailLayout.error = "Email is required"
                binding.emailLayout.isErrorEnabled = true
                hasError = true
            }

            if (password.isEmpty()) {
                binding.passwordLayout.error = "Password is required"
                binding.passwordLayout.isErrorEnabled = true
                hasError = true
            }

            if (hasError) return@setOnClickListener

            // Call ViewModel
            loginViewModel.signIn(email, password, client)
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResponseState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        // Clear previous errors
                        binding.emailLayout.error = null
                        binding.emailLayout.isErrorEnabled = false
                        binding.passwordLayout.error = null
                        binding.passwordLayout.isErrorEnabled = false

                        state.items?.let { user ->
                            // SAVE THE TOKEN HERE
                            user.jwtToken?.let { token ->
                                secureStorage.saveToken(token)
                            }
                            navigateToRoleActivity(user)
                        }
                    }
                    is ResponseState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        // Show API error in email layout
                        binding.emailLayout.error = state.message
                        binding.emailLayout.isErrorEnabled = true
                    }
                    is ResponseState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun navigateToRoleActivity(user: GetUserByEmailAndPasswordResult) {
        val intent = when (user.role.toRoleString()) {
            "Admin" -> Intent(requireContext(), AdminAppActivity::class.java)
            "Customer" -> Intent(requireContext(), CustomerAppActivity::class.java)
            else -> Intent(requireContext(), MainActivity::class.java)
        }
        startActivity(intent)
        requireActivity().finish() // Close LoginFragment / MainActivity
    }
}