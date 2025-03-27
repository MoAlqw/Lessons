package com.example.a01_criminalintent.view.fragment.dialog

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.a01_criminalintent.R

class ImageShowFragment: DialogFragment() {

    @Suppress("DEPRECATION")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val photoUri = if (android.os.Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(ARG_URI, Uri::class.java)
        } else {
            arguments?.getParcelable(ARG_URI)
        }

        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.fragment_image_crime)

        val image = dialog.findViewById<ImageView>(R.id.img_crime)
        val btnClose = dialog.findViewById<ImageButton>(R.id.btn_close_fragment)

        image.setImageURI(photoUri)
        btnClose.setOnClickListener { dismiss() }

        return dialog
    }

    companion object {
        private const val ARG_URI = "image_uri"

        fun newInstance(photoUri: Uri): ImageShowFragment {
            return ImageShowFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_URI, photoUri)
                }
            }
        }
    }

}