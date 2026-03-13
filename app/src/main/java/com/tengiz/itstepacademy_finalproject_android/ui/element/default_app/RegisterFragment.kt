package com.tengiz.itstepacademy_finalproject_android.ui.element.default_app

import android.util.Patterns
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.datepicker.MaterialDatePicker
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentRegisterBinding
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun setup() {
        setupRealTimeValidation()
    }

    override fun listeners() {
        super.listeners()

        // DOB picker
        binding.dobEditText.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date of Birth")
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener { selection ->
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selection))
                binding.dobEditText.setText(date)
            }
            picker.show(parentFragmentManager, picker.toString())
        }

        // Register button
        binding.registerBtn.setOnClickListener {
            if (validateForm()) {
                // Proceed with registration
            }
        }
    }

    private fun setupRealTimeValidation() {
        binding.firstNameEditText.doAfterTextChanged { validateFirstName() }
        binding.lastNameEditText.doAfterTextChanged { validateLastName() }
        binding.emailEditText.doAfterTextChanged { validateEmail() }
        binding.dobEditText.doAfterTextChanged { validateDOB() }
        binding.passwordEditText.doAfterTextChanged {
            validatePassword()
            validateRepeatPassword()
        }
        binding.repeatPasswordEditText.doAfterTextChanged { validateRepeatPassword() }
        binding.address1EditText.doAfterTextChanged { validateAddress1() }
        binding.cityEditText.doAfterTextChanged { validateCity() }
    }

    // --- VALIDATION FUNCTIONS ---

    private fun validateFirstName() {
        val text = binding.firstNameEditText.text.toString().trim()
        when {
            text.isEmpty() -> {
                binding.firstNameLayout.error = "First name field is required"
                binding.firstNameLayout.isErrorEnabled = true
            }
            text.length < 2 -> {
                binding.firstNameLayout.error = "First name must be at least 2 characters"
                binding.firstNameLayout.isErrorEnabled = true
            }
            text.length > 60 -> {
                binding.firstNameLayout.error = "First name must be less than 61 characters"
                binding.firstNameLayout.isErrorEnabled = true
            }
            else -> {
                binding.firstNameLayout.error = null
                binding.firstNameLayout.isErrorEnabled = false
            }
        }
    }

    private fun validateLastName() {
        val text = binding.lastNameEditText.text.toString().trim()
        when {
            text.isEmpty() -> {
                binding.lastNameLayout.error = "Last name field is required"
                binding.lastNameLayout.isErrorEnabled = true
            }
            text.length < 2 -> {
                binding.lastNameLayout.error = "Last name must be at least 2 characters"
                binding.lastNameLayout.isErrorEnabled = true
            }
            text.length > 60 -> {
                binding.lastNameLayout.error = "Last name must be less than 61 characters"
                binding.lastNameLayout.isErrorEnabled = true
            }
            else -> {
                binding.lastNameLayout.error = null
                binding.lastNameLayout.isErrorEnabled = false
            }
        }
    }

    private fun validateEmail() {
        val text = binding.emailEditText.text.toString().trim()
        when {
            text.isEmpty() -> {
                binding.emailLayout.error = "Email field is required"
                binding.emailLayout.isErrorEnabled = true
            }
            !Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
                binding.emailLayout.error = "Email format is not valid"
                binding.emailLayout.isErrorEnabled = true
            }
            else -> {
                binding.emailLayout.error = null
                binding.emailLayout.isErrorEnabled = false
            }
        }
    }

    private fun validateDOB() {
        val text = binding.dobEditText.text.toString().trim()
        when {
            text.isEmpty() -> {
                binding.dobLayout.error = "Date of birth is required"
                binding.dobLayout.isErrorEnabled = true
            }
            else -> {
                val dob = try { LocalDate.parse(text) } catch (e: Exception) { null }
                val today = LocalDate.now()
                when {
                    dob == null -> { binding.dobLayout.error = "Invalid date format"; binding.dobLayout.isErrorEnabled = true }
                    dob.isAfter(today) -> { binding.dobLayout.error = "Person can't be born in the future"; binding.dobLayout.isErrorEnabled = true }
                    dob.isBefore(today.minusYears(160)) -> { binding.dobLayout.error = "Person can't be more than 160 years old"; binding.dobLayout.isErrorEnabled = true }
                    else -> { binding.dobLayout.error = null; binding.dobLayout.isErrorEnabled = false }
                }
            }
        }
    }

    private fun validatePassword() {
        val password = binding.passwordEditText.text.toString()
        val regex = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        when {
            password.isEmpty() -> { binding.passwordLayout.error = "Paasword field is required"; binding.passwordLayout.isErrorEnabled = true }
            !regex.matches(password) -> { binding.passwordLayout.error = "Password must be at least 8 characters, contain uppercase, lowercase, and a number"; binding.passwordLayout.isErrorEnabled = true }
            else -> { binding.passwordLayout.error = null; binding.passwordLayout.isErrorEnabled = false }
        }
    }

    private fun validateRepeatPassword() {
        val password = binding.passwordEditText.text.toString()
        val repeat = binding.repeatPasswordEditText.text.toString()
        when {
            repeat != password -> { binding.repeatPasswordLayout.error = "Passwords do not match"; binding.repeatPasswordLayout.isErrorEnabled = true }
            else -> { binding.repeatPasswordLayout.error = null; binding.repeatPasswordLayout.isErrorEnabled = false }
        }
    }

    private fun validateAddress1() {
        val text = binding.address1EditText.text.toString().trim()
        when {
            text.isEmpty() -> { binding.address1Layout.error = "Address 1 field is required"; binding.address1Layout.isErrorEnabled = true }
            else -> { binding.address1Layout.error = null; binding.address1Layout.isErrorEnabled = false }
        }
    }

    private fun validateCity() {
        val text = binding.cityEditText.text.toString().trim()
        when {
            text.isEmpty() -> { binding.cityLayout.error = "City field is required"; binding.cityLayout.isErrorEnabled = true }
            else -> { binding.cityLayout.error = null; binding.cityLayout.isErrorEnabled = false }
        }
    }

    private fun validateForm(): Boolean {
        validateFirstName()
        validateLastName()
        validateEmail()
        validateDOB()
        validatePassword()
        validateRepeatPassword()
        validateAddress1()
        validateCity()

        return listOf(
            binding.firstNameLayout.error,
            binding.lastNameLayout.error,
            binding.emailLayout.error,
            binding.dobLayout.error,
            binding.passwordLayout.error,
            binding.repeatPasswordLayout.error,
            binding.address1Layout.error,
            binding.cityLayout.error
        ).all { it == null }
    }
}