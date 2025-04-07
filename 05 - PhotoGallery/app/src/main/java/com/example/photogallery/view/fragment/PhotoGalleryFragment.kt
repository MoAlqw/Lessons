package com.example.photogallery.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.FragmentPhotoGalleryBinding
import com.example.photogallery.model.DataState
import com.example.photogallery.model.Fact
import com.example.photogallery.model.repository.CatFactRepository
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
                is DataState.Success -> successResponse(fact)
                is DataState.Error -> errorResponse(fact)

            }
        }
    }

    private fun updateUI(fact: String, imageUrl: String) {
        with(binding) {
            tvFactAboutCat.text = fact
            Glide.with(requireContext())
                .load(imageUrl)
                .into(imgCat)

            progressBar.visibility = View.GONE
            containerFact.visibility = View.VISIBLE
        }
    }

    private fun successResponse(fact: DataState.Success<Fact>) {
        val factText = fact.data.fact
        val imageUrl = "https://http.cat/200"
        updateUI(factText, imageUrl)
    }

    private fun errorResponse(error: DataState.Error<Fact>) {
        val errorMessage = error.message
        val imageUrl = "https://http.cat/${error.code}"
        updateUI(errorMessage, imageUrl)
    }

    companion object {

        fun newInstance(): PhotoGalleryFragment {
            return PhotoGalleryFragment()
        }

    }
}