package com.hyun.tpmanbo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyun.tpmanbo.databinding.ActivityMainBinding
import com.hyun.tpmanbo.databinding.FragmentMapBinding
import com.hyun.tpmanbo.databinding.FragmentStepBinding

class MapFragment : Fragment() {

    lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMapBinding.inflate(inflater,container, false)

        return binding.root

    }

}

