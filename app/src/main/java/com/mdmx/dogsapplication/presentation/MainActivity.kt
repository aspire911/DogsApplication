package com.mdmx.dogsapplication.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdmx.dogsapplication.R
import com.mdmx.dogsapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ImageGridViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImageGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupObservers()
        viewModel.initialLoad()
    }

    private fun setupRecyclerView() {
        adapter = ImageGridAdapter(resources.getInteger(R.integer.grid_image_size))
        val layoutManager = GridLayoutManager(applicationContext, 2)
        binding.recyclerViewImageGrid.layoutManager = layoutManager
        binding.recyclerViewImageGrid.adapter = adapter
        binding.recyclerViewImageGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.onLastItemVisible()
                }
            }
        })
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.apply {
                    if (state.isLoading) {
                        textViewError.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                        return@collect
                    }
                    if (!state.errorMessage.isNullOrBlank()) {
                        progressBar.visibility = View.INVISIBLE
                        textViewError.visibility = View.VISIBLE
                        textViewError.setTextColor(Color.RED)
                        textViewError.text = state.errorMessage
                        return@collect
                    }
                    textViewError.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE
                    adapter.addItems(state.listOfURLs)
                }
            }
        }
    }
}