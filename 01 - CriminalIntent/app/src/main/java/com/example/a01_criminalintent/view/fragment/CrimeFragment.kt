package com.example.a01_criminalintent.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.a01_criminalintent.databinding.FragmentCrimeBinding
import com.example.a01_criminalintent.view.fragment.dialog.DatePickerFragment
import com.example.a01_criminalintent.view.fragment.dialog.TimePickerFragment
import com.example.a01_criminalintent.viewmodel.CrimeFragmentViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class CrimeFragment: Fragment() {

    private var _binding: FragmentCrimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var crimeContainer: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private val viewModel: CrimeFragmentViewModel by viewModels {
        CrimeFragmentViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeBinding.inflate(inflater, container, false)
        crimeContainer = binding.crimeContainer
        progressBar = binding.progressBar
        titleField = binding.crimeTitle
        dateButton = binding.crimeDate
        solvedCheckBox = binding.crimeSolved
        timeButton = binding.crimeTime

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentCrime.observe(viewLifecycleOwner) {
            if (it.title != titleField.text.toString()) titleField.setText(it.title)
            dateButton.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it.date)
            timeButton.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(it.date)
            solvedCheckBox.apply {
                isChecked = it.isSolved
                jumpDrawablesToCurrentState()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == false) {
                crimeContainer.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }

        @Suppress("DEPRECATION")
        parentFragmentManager.setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE, viewLifecycleOwner) { _, bundle ->
            val result: Date = if (android.os.Build.VERSION.SDK_INT >= 33) {
                bundle.getSerializable(DatePickerFragment.KEY_SELECTED_DATE, Date::class.java)
            } else {
                bundle.getSerializable(DatePickerFragment.KEY_SELECTED_DATE) as Date
            } ?: Date()
            viewModel.updateCrime { it.copy(date = result) }
        }

        parentFragmentManager.setFragmentResultListener(TimePickerFragment.REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            val result: IntArray = bundle.getIntArray(TimePickerFragment.SELECTED_TIME) ?: intArrayOf(0, 0)

            viewModel.updateCrime { crime ->
                val calendar = Calendar.getInstance().apply {
                    time = crime.date
                    set(Calendar.HOUR_OF_DAY, result[0])
                    set(Calendar.MINUTE, result[1])
                }
                crime.copy(date = calendar.time)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateCrime { it.copy(title = s.toString()) }
            }
            override fun afterTextChanged(s: Editable?) { }
        }
        titleField.addTextChangedListener(titleWatcher)
        solvedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateCrime { it.copy(isSolved = isChecked) }
        }

        viewModel.currentCrime.observe(viewLifecycleOwner) { crime ->
            dateButton.setOnClickListener {
                DatePickerFragment.newInstance(crime.date).apply {
                    show(this@CrimeFragment.parentFragmentManager, DIALOG_TAG_DATE)
                }
            }

            val calendar = Calendar.getInstance().apply { time = crime.date }
            val hours = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)
            timeButton.setOnClickListener {
                TimePickerFragment.newInstance(intArrayOf(hours, minutes)).apply {
                    show(this@CrimeFragment.parentFragmentManager, DIALOG_TAG_TIME)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveCrime()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_CRIME_ID = "crime_id"
        const val DIALOG_TAG_DATE = "dialog_date"
        const val DIALOG_TAG_TIME = "dialog_time"

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }

}