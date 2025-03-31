package com.example.a01_criminalintent.view.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.a01_criminalintent.R
import com.example.a01_criminalintent.databinding.FragmentCrimeBinding
import com.example.a01_criminalintent.view.fragment.dialog.DatePickerFragment
import com.example.a01_criminalintent.view.fragment.dialog.ImageShowFragment
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
    private lateinit var crimeContainer: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var imgCrime: ImageView
    private lateinit var btnSetImage: ImageButton
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var suspectButton: Button
    private lateinit var sendReportButton: Button
    private lateinit var photoUri: Uri
    private val viewModel: CrimeFragmentViewModel by viewModels {
        CrimeFragmentViewModel.Factory
    }
    private lateinit var requestPermission: ActivityResultLauncher<String>
    private lateinit var pickContact: ActivityResultLauncher<Void?>
    private lateinit var takePhoto: ActivityResultLauncher<Uri>

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
        suspectButton = binding.btnChooseSuspect
        sendReportButton = binding.btnSendReport
        imgCrime = binding.imgCrime
        btnSetImage = binding.btnSetImage

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickContact = registerForActivityResult(ActivityResultContracts.PickContact()) { result: Uri? ->
            if (result != null) {
                getContactName(result)
            }
        }

        takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                imgCrime.setImageURI(null)
                imgCrime.setImageURI(photoUri)
            }
        }

        requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                pickContact.launch(null)
            } else {
                Toast.makeText(requireContext(), "Permission was decline", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.currentCrime.observe(viewLifecycleOwner) {
            if (it.title != titleField.text.toString()) titleField.setText(it.title)
            dateButton.text =
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it.date)
            timeButton.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(it.date)
            solvedCheckBox.apply {
                isChecked = it.isSolved
                jumpDrawablesToCurrentState()
            }
        }

        viewModel.photoFile.observe(viewLifecycleOwner) {
            if (it != null) {
                photoUri = FileProvider.getUriForFile(requireContext(), "com.example.a01_criminalintent.fileprovider", it)
                imgCrime.setImageURI(photoUri)
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

        suspectButton.setOnClickListener {
            checkPermissionContacts()
        }

        sendReportButton.setOnClickListener {
            createIntent()
        }

        btnSetImage.setOnClickListener{
            takePhoto.launch(photoUri)
        }

        imgCrime.setOnClickListener {
            val dialog = ImageShowFragment.newInstance(photoUri)
            dialog.showNow(parentFragmentManager, null)
        // the second option, when changing the configuration, renders faster
        // but overall slower and with ActionBar
//            val fragment = ImageFragment.newInstance(photoUri)
//            parentFragmentManager.commit {
//                replace(R.id.fragment_container, fragment)
//                addToBackStack(null)
//            }
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



    private fun getCrimeReport(): String {
        val crime = viewModel.currentCrime.value ?: throw IllegalStateException("Crime is not load")
        val solvedString = if (crime.isSolved) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }
        val dateString = SimpleDateFormat("EEE, MMM, dd", Locale.getDefault()).format(crime.date)
        val suspect = if (crime.suspect.isBlank()) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect, crime.suspect)
        }
        return getString(
            R.string.crime_report,
            crime.title, dateString, solvedString, suspect)
    }

    private fun getContactName(contactUri: Uri) {
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        requireContext().contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(projection[0])
                val contactName = cursor.getString(nameIndex)
                viewModel.updateCrime { it.copy(suspect = contactName) }
            }
        }
    }

    private fun createIntent() {
        val sms = getCrimeReport()
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, sms)
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
        }
        startActivity(Intent.createChooser(intent, getString(R.string.send_report)))
    }

    private fun checkPermissionContacts() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            pickContact.launch(null)
        } else  {
            requestPermission.launch(android.Manifest.permission.READ_CONTACTS)
        }
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