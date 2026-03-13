package com.tengiz.itstepacademy_finalproject_android.ui.element.default_app

import com.google.android.material.datepicker.MaterialDatePicker
import com.tengiz.itstepacademy_finalproject_android.R
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentRegisterBinding
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun setup() {}

    override fun listeners() {
        super.listeners()

        binding.dobEditText.setOnClickListener {
            // Build MaterialDatePicker
            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date of Birth") // Optional title

            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { selection ->
                // selection is milliseconds
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selection))
                binding.dobEditText.setText(date)
            }

            picker.show(parentFragmentManager, picker.toString())
        }
    }
}