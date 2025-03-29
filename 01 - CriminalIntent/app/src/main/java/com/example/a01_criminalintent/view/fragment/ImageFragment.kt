package com.example.a01_criminalintent.view.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.a01_criminalintent.databinding.FragmentImageCrimeBinding

class ImageFragment: Fragment() {
    private var _binding: FragmentImageCrimeBinding? =  null
    private val binding get() = _binding!!
    private lateinit var imageCrime: ImageView
    private lateinit var btnClose: ImageButton
    private var photoUri: Uri? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoUri = if (android.os.Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(PHOTO_URI, Uri::class.java)
        } else {
            arguments?.getParcelable(PHOTO_URI)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageCrimeBinding.inflate(inflater, container, false)
        imageCrime = binding.imgCrime
        btnClose = binding.btnCloseFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageCrime.setImageURI(photoUri)

        btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val PHOTO_URI = "photo_uri"

        fun newInstance(photoUri: Uri): ImageFragment {
            val arg = Bundle().apply {
                putParcelable(PHOTO_URI, photoUri)
            }
            return ImageFragment().apply {
                arguments = arg
            }
        }
    }

}