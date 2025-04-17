package com.example.photoapi.view.fragment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoapi.R
import com.example.photoapi.viewmodel.PhotoViewModel
import com.example.photoapi.databinding.FragmentPhotosBinding
import com.example.photoapi.model.DataState
import com.example.photoapi.model.repository.UnsplashRepository
import com.example.photoapi.view.adapter.PhotoAdapter
import com.example.photoapi.viewmodel.PhotoViewModelFactory

class PhotosFragment : VisibleFragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PhotoAdapter

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private val viewModel: PhotoViewModel by viewModels {
        PhotoViewModelFactory(UnsplashRepository(),
            requireActivity().application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        adapter = PhotoAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.openSearchViewToolbar) { toolbar, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            toolbar.setPadding(
                toolbar.paddingLeft,
                statusBarHeight,
                toolbar.paddingRight,
                toolbar.paddingBottom
            )
            insets
        }

        binding.recyclerViewPhoto.adapter = adapter
        binding.recyclerViewPhoto.layoutManager = GridLayoutManager(requireContext(), 2)

        checkPermission()

        viewModel.menuItemTitle.observe(viewLifecycleOwner) {
            binding.openSearchViewToolbar.menu.findItem(R.id.menu_item_toggle_polling).setTitle(it)
        }

        binding.openSearchViewToolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_item_toggle_polling -> {
                    viewModel.itemPolling()
                    true
                }
                else -> false
            }
        }

        val searchView = binding.openSearchViewToolbar.menu.findItem(R.id.menu_item_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) viewModel.getPhotos(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.photos.observe(viewLifecycleOwner) { result ->
            when(result) {
                is DataState.Success -> {
                    adapter.submitList(result.data.result)
                    showOnly(binding.recyclerViewPhoto)
                }
                is DataState.Error -> showOnly(binding.textViewError)
                is DataState.Loading -> showOnly(binding.progressBar)
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showOnly(view: View) {
        binding.recyclerViewPhoto.visibility = if (view == binding.recyclerViewPhoto) View.VISIBLE else View.GONE
        binding.progressBar.visibility = if (view == binding.progressBar) View.VISIBLE else View.GONE
        binding.textViewError.visibility = if (view == binding.textViewError) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PhotosFragment()
    }
}