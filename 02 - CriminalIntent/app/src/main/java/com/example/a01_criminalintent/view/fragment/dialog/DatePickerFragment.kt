package com.example.a01_criminalintent.view.fragment.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import java.util.Date

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date: Date = if (android.os.Build.VERSION.SDK_INT >= 33) {
            arguments?.getSerializable(ARG_DATE, Date::class.java)
        } else {
            arguments?.getSerializable(ARG_DATE) as Date
        } ?: Date()
        val calendar = Calendar.getInstance().apply { time = date }
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            this,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val pickedDate = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }.time

        parentFragmentManager.setFragmentResult(REQUEST_KEY_DATE, bundleOf(KEY_SELECTED_DATE to pickedDate))
    }

    companion object {
        const val ARG_DATE = "date"
        const val REQUEST_KEY_DATE = "request_key_date"
        const val KEY_SELECTED_DATE = "selected_date"

        fun newInstance(date: Date): DatePickerFragment {
            val arg = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = arg
            }
        }
    }

}