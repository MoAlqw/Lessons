package com.example.a01_criminalintent.view.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_criminalintent.databinding.FragmentCrimeListBinding
import com.example.a01_criminalintent.model.Crime
import com.example.a01_criminalintent.model.adapter.AdapterCrimeList
import com.example.a01_criminalintent.viewmodel.CrimeListViewModel

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var _binding: FragmentCrimeListBinding? = null
    private val binding get() = _binding!!
    private var adapter: AdapterCrimeList? = null
    private val viewModel: CrimeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        crimeRecyclerView = binding.rvCrimeList
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.data.observe(viewLifecycleOwner) {
            updateUI(it)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = AdapterCrimeList(crimes)
        crimeRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}