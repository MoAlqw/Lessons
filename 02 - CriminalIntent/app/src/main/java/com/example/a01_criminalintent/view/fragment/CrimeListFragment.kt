package com.example.a01_criminalintent.view.fragment

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_criminalintent.R
import com.example.a01_criminalintent.databinding.FragmentCrimeListBinding
import com.example.a01_criminalintent.model.adapter.AdapterCrimeList
import com.example.a01_criminalintent.model.room.Crime
import com.example.a01_criminalintent.viewmodel.CrimeListViewModel
import java.util.UUID

class CrimeListFragment : Fragment() {
    private var callbacks: Callbacks? = null
    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var contentEmpty: LinearLayout
    private lateinit var btnAddCrime: Button
    private var _binding: FragmentCrimeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CrimeListViewModel by viewModels()
    private var adapter: AdapterCrimeList? = null

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

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
        contentEmpty = binding.linearContentEmpty
        btnAddCrime = binding.btnAddCrime
        btnAddCrime.setOnClickListener {
            createCrime()
        }

        adapter = AdapterCrimeList(callbacks)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                contentEmpty.visibility = View.GONE
                btnAddCrime.setOnClickListener(null)
                crimeRecyclerView.visibility = View.VISIBLE
            }
            adapter?.submitList(it)
        }
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_crime_list, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.new_crime -> {
                        createCrime()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun createCrime() {
        val crime = Crime()
        viewModel.addCrimeAndSelect(crime) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}