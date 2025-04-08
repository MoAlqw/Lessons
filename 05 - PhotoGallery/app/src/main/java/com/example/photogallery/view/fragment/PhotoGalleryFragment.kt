package com.example.photogallery.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.FragmentPhotoGalleryBinding
import com.example.photogallery.model.DataState
import com.example.photogallery.model.Fact
import com.example.photogallery.model.repository.CatFactRepository
import com.example.photogallery.view.adapter.RecyclerCatAdapter
import com.example.photogallery.viewmodel.GalleryFragmentViewModel
import com.example.photogallery.viewmodel.factory.CatFactViewModelFactory

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
                is DataState.Loading -> { updateUI(fact) }
                is DataState.Success -> {
                    binding.rvCats.adapter = RecyclerCatAdapter(fact)
                    binding.rvCats.layoutManager = GridLayoutManager(requireContext(), 2)
                    updateUI(fact)
                }
                is DataState.Error -> { updateUI(fact) }
            }
        }
        binding.btnRetryLoad.setOnClickListener { viewModel.tryToLoadFacts() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(state: DataState<List<Fact>>) {
        when(state) {
            is DataState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvCats.visibility = View.GONE
                binding.containerErrorLoad.visibility = View.GONE
            }
            is DataState.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.containerErrorLoad.visibility = View.GONE
                binding.rvCats.visibility = View.VISIBLE
            }
            is DataState.Error -> {
                Glide.with(requireContext())
                    .load(state.url + state.code)
                    .into(binding.imgResponse)
                binding.progressBar.visibility = View.GONE
                binding.rvCats.visibility = View.GONE
                binding.containerErrorLoad.visibility = View.VISIBLE
            }
        }
    }

    companion object {

        fun newInstance(): PhotoGalleryFragment {
            return PhotoGalleryFragment()
        }
    }
}