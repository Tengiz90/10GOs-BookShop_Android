package com.tengiz.itstepacademy_finalproject_android.ui.element.customer_app

import LoadingStateAdapter
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.tengiz.itstepacademy_finalproject_android.databinding.FragmentHomeCustomerAppBinding
import com.tengiz.itstepacademy_finalproject_android.ui.adapter.HomeBooksAdapter
import com.tengiz.itstepacademy_finalproject_android.ui.common.BaseFragment
import com.tengiz.itstepacademy_finalproject_android.ui.viewmodel.HomeBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeCustomerAppFragment : BaseFragment<FragmentHomeCustomerAppBinding>(FragmentHomeCustomerAppBinding::inflate) {

    private val viewModel: HomeBooksViewModel by viewModels()
    private lateinit var adapter: HomeBooksAdapter

    override fun setup() {
        setupRecycler()
        observeBooks()
        setupSearchView()
        observeLoadStates()
    }

    private fun setupRecycler() {
        adapter = HomeBooksAdapter(
            onItemClick = { book ->

            },
            onAuthorClick = { author ->

                val action = HomeCustomerAppFragmentDirections.actionHomeCustomerAppFragmentToAuthorFragment(author)
                findNavController().navigate(action)
            }
        )

        val recycler = binding.itemsRV

        // Use GridLayoutManager with 2 columns
        val layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                val viewType = binding.itemsRV.adapter?.getItemViewType(position)

                return if (viewType == LoadingStateAdapter.VIEW_TYPE_LOADING) {
                    2 // loading footer spans both columns
                } else {
                    1
                }
            }
        }

        recycler.layoutManager = layoutManager
        recycler.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )
    }

    private fun observeBooks() {
        lifecycleScope.launch {
            viewModel.booksFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchBooks(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchBooks(newText)
                return true
            }
        })
    }

    private fun observeLoadStates() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadStates ->
                val refreshState = loadStates.refresh

                // Top progress bar
                binding.progressBar.visibility =
                    if (refreshState is LoadState.Loading) View.VISIBLE
                    else View.GONE

                // Error message
                binding.errorMessage.visibility =
                    if (refreshState is LoadState.Error) View.VISIBLE
                    else View.GONE
            }
        }
    }
}