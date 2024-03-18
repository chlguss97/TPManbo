package com.hyun.tpmanbo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyun.tpmanbo.databinding.FragmentCalendarBinding
import com.hyun.tpmanbo.databinding.FragmentInfoBinding

class InfoFragment : Fragment(){

    val binding by lazy { FragmentInfoBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

}