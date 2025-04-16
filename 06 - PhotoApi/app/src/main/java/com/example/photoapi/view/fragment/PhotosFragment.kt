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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoapi.R
import com.example.photoapi.viewmodel.PhotoViewModel
import com.example.photoapi.databinding.FragmentPhotosBinding
import com.example.photoapi.model.DataState
import com.example.photoapi.model.repository.UnsplashRepository
import com.example.photoapi.view.adapter.PhotoAdapter
import com.example.photoapi.viewmodel.PhotoViewModelFactory
import com.example.photoapi.worker.PollWorker

class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PhotoAdapter

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) PollWorker.enqueue(requireContext())
        }

    private val viewModel: PhotoViewModel by viewModels {
        PhotoViewModelFactory(UnsplashRepository())
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
        binding.listViewPhoto.adapter = adapter
        binding.listViewPhoto.layoutManager = GridLayoutManager(requireContext(), 2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                PollWorker.enqueue(requireContext())
            }
        }

        val searchView = binding.openSearchViewToolbar.menu.findItem(R.id.menu_item_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query != "") viewModel.getPhotos(query)
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
                    binding.listViewPhoto.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.textViewError.visibility = View.GONE
                }
                is DataState.Error -> {
                    binding.listViewPhoto.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.textViewError.visibility = View.VISIBLE
                }
                is DataState.Loading -> {
                    binding.listViewPhoto.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PhotosFragment()
    }
}