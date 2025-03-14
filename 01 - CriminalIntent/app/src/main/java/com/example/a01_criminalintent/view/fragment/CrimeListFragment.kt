package com.example.a01_criminalintent.view.fragment

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_criminalintent.databinding.FragmentCrimeListBinding
import com.example.a01_criminalintent.model.adapter.AdapterCrimeList
import com.example.a01_criminalintent.model.callback.Callbacks
import com.example.a01_criminalintent.viewmodel.CrimeListViewModel

class CrimeListFragment : Fragment() {
    private var callbacks: Callbacks? = null
    private lateinit var crimeRecyclerView: RecyclerView
    private var _binding: FragmentCrimeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CrimeListViewModel by viewModels()
    private var adapter: AdapterCrimeList? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Callbacks) {
            callbacks = context
        } else {
            throw ClassCastException("$context should be Callbacks")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        crimeRecyclerView = binding.rvCrimeList
        adapter = AdapterCrimeList(callbacks)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}