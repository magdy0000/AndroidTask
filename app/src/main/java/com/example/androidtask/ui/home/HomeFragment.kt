package com.example.androidtask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidtask.R
import com.example.androidtask.adapters.HomeRecyclerAdapter


import com.example.androidtask.data.network.NetworkState
import com.example.androidtask.databinding.FragmentHomeBinding
import com.example.androidtask.utils.ProgressLoading
import com.example.androidtask.utils.showToast
import com.example.androidtask.data.models.MovieModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val recyclerAdapter by lazy { HomeRecyclerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        homeViewModel.getAllMovies()
        observers()
        onClicks()
    }

    private fun onClicks() {


    }

    private fun observers() {
        homeViewModel.moviesLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                NetworkState.Status.RUNNING -> {

                    ProgressLoading.show(requireActivity())
                }
                NetworkState.Status.SUCCESS -> {

                    val data = it.data as ArrayList<MovieModel>
                    recyclerAdapter.listAllMovies = data
                    binding.recyclerMovies.adapter= recyclerAdapter
                    ProgressLoading.dismiss()
                }
                else -> {
                    ProgressLoading.dismiss()
                    showToast(it.msg!!)
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}