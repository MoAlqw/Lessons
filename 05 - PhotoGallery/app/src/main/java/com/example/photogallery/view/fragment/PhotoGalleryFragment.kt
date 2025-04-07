package com.example.photogallery.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.databinding.FragmentPhotoGalleryBinding
import com.example.photogallery.model.DataState
import com.example.photogallery.model.repository.CatFactRepository
import com.example.photogallery.view.adapter.RecyclerCatAdapter
import com.example.photogallery.viewmodel.CatFactViewModelFactory
import com.example.photogallery.viewmodel.GalleryFragmentViewModel

class PhotoGalleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryFragmentViewModel by viewModels {
        CatFactViewModelFactory(CatFactRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.factCat.observe(viewLifecycleOwner) { fact ->
            when(fact) {
                is DataState.Loading -> { }
                is DataState.Success -> {
                    binding.rvCats.adapter = RecyclerCatAdapter(fact)
                    binding.rvCats.layoutManager = GridLayoutManager(requireContext(), 2)
                    updateUI()
                }
                is DataState.Error -> { }

            }
        }
    }

    private fun updateUI() {
        with(binding) {
            progressBar.visibility = View.GONE
            rvCats.visibility = View.VISIBLE
        }
    }



    companion object {

        fun newInstance(): PhotoGalleryFragment {
            return PhotoGalleryFragment()
        }

    }
}