package com.tengiz.itstepacademy_finalproject_android.ui.element.default_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tengiz.itstepacademy_finalproject_android.R
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentDefaultAppHomeBinding
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment
import com.tengiz.itstepacademy_finalproject_android.ui.element.customer_app.HomeCustomerAppFragmentDirections


class DefaultAppHomeFragment : BaseFragment<FragmentDefaultAppHomeBinding>(FragmentDefaultAppHomeBinding::inflate) {
    override fun setup() {

    }

    override fun listeners() {
        super.listeners()

        binding.registerBtn.setOnClickListener {
            val action = DefaultAppHomeFragmentDirections.actionDefaultAppHomeFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        binding.signBtn.setOnClickListener {
            val action = DefaultAppHomeFragmentDirections.actionDefaultAppHomeFragmentToLoginFragment()
            findNavController().navigate(action);
        }
    }



}