package com.example.a01_criminalintent.view.fragment.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment

class TimePickerFragment: DialogFragment(),  TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val time = arguments?.getIntArray(ARG_TIME)
        val hours = time?.getOrNull(0) ?: 0
        val minutes = time?.getOrNull(1) ?: 0

        return TimePickerDialog(
            requireContext(),
            this,
            hours,
            minutes,
            true
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val result = bundleOf(SELECTED_TIME to intArrayOf(hourOfDay, minute))
        parentFragmentManager.setFragmentResult(REQUEST_KEY, result)
    }

    companion object {
        const val ARG_TIME = "time"
        const val SELECTED_TIME = "selected_time"
        const val REQUEST_KEY = "request_key_time"

        fun newInstance(time: IntArray): TimePickerFragment {
            val arg = Bundle().apply {
                putIntArray(ARG_TIME, time)
            }
            return TimePickerFragment().apply {
                arguments = arg
            }
        }
    }
}