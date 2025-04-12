package com.example.photogallery.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.databinding.FragmentPhotoGalleryBinding
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

        val adapter = RecyclerCatAdapter()
        with(binding) {
            rvCats.layoutManager = GridLayoutManager(requireContext(), 2)
            rvCats.adapter = adapter
        }

        viewModel.factsPager.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(): PhotoGalleryFragment {
            return PhotoGalleryFragment()
        }
    }
}