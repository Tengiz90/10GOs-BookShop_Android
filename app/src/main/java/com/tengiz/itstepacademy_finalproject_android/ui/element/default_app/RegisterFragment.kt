package com.tengiz.itstepacademy_finalproject_android.ui.element.default_app

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tengiz.itstepacademy_finalproject_android.R
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentRegisterBinding
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override fun setup() {

    }

    override fun listeners() {
        super.listeners()

        binding.dobEditText.setOnClickListener {

            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val date = "$year-${month + 1}-$day"
                    binding.dobEditText.setText(date)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
    }


}