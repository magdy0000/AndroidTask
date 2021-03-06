package com.example.androidtask.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidtask.R
import com.example.androidtask.databinding.FragmentDetailsBinding
import com.example.androidtask.databinding.FragmentHomeBinding
import com.example.androidtask.utils.Const

class DetailsFragment : Fragment () {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details , container , false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        initData()
    }

    private fun initData (){
        DetailsFragmentArgs.fromBundle(requireArguments()).apply {
            binding.apply {
                Glide.with(requireContext())
                    .load(Const.BASE_IMAGE+image).placeholder(R.drawable.ic_place_holder)
                    .into(imageMovie)

                textDesc.text = description
                textTitle.text = title
                textReleaseDate.text= getString(R.string.release_date) + date
                textVoteCount.text = getString(R.string.vote) +vote
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}