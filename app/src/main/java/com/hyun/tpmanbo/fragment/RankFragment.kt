package com.hyun.tpmanbo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyun.tpmanbo.databinding.FragmentRankBinding

class RankFragment : Fragment() {

    lateinit var binding : FragmentRankBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRankBinding.inflate(layoutInflater)

        return binding.root
    }
}