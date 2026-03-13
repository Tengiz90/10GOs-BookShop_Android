package com.tengiz.itstepacademy_finalproject_android.ui.element.customer_app

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentCategoryBinding
import com.tengiz.itstepacademy_finalproject_android.ui.adapter.CategoriesAdapter
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment
import com.tengiz.itstepacademy_finalproject_android.ui.viewmodel.CategoriesViewModel
import com.tengiz.itstepacademy_finalproject_android.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
) {

    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun setup() {
        // Initialize adapter with click listener
        categoriesAdapter = CategoriesAdapter { category ->
            Toast.makeText(requireContext(), "Clicked: ${category.type}", Toast.LENGTH_SHORT).show()
        }

        // Setup RecyclerView
        binding.itemsRV.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Fetch categories
        viewModel.getCategories()
    }
    override fun observers() {
        super.observers()
        // Collect categories state
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categoriesState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResponseState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        categoriesAdapter.submitList(state.items) // displays data
                    }
                    is ResponseState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                        categoriesAdapter.submitList(emptyList()) // ensures nothing lingering
                    }
                }
            }
        }
    }
}