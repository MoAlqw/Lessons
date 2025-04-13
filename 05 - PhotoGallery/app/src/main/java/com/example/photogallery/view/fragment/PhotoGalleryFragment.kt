package com.example.photogallery.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.R
import com.example.photogallery.databinding.FragmentPhotoGalleryBinding
import com.example.photogallery.model.repository.CatFactRepository
import com.example.photogallery.view.adapter.RecyclerCatAdapter
import com.example.photogallery.viewmodel.GalleryFragmentViewModel
import com.example.photogallery.viewmodel.factory.CatFactViewModelFactory
import kotlin.math.max

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
        binding.rvCats.adapter = adapter


        viewModel.factsPager.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.rvCats.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val columnWidthPx = resources.getDimensionPixelSize(R.dimen.column_width)
                    val recyclerViewWidth = binding.rvCats.width

                    if (recyclerViewWidth > 0) {
                        val spanCount = max(1, recyclerViewWidth/columnWidthPx)
                        binding.rvCats.layoutManager = GridLayoutManager(requireContext(), spanCount)
                        binding.rvCats.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            }
        )

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