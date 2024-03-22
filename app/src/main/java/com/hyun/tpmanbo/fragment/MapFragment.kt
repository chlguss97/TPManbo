package com.hyun.tpmanbo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hyun.tpmanbo.activities.MainActivity
import com.hyun.tpmanbo.databinding.ActivityMainBinding
import com.hyun.tpmanbo.databinding.FragmentMapBinding
import com.hyun.tpmanbo.databinding.FragmentStepBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapReadyCallback
import com.kakao.vectormap.MapView
import java.lang.Exception

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapview.start(object :MapLifeCycleCallback(){
            override fun onMapDestroy() {
                Toast.makeText(requireContext(), "맵을 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onMapError(p0: Exception?) {
                Toast.makeText(requireContext(), "맵을 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(p0: KakaoMap) {
                val latitude: Double = (activity as MainActivity).myLocation?.latitude ?: 37.5666
                val longitude: Double =  (activity as MainActivity).myLocation?.longitude ?: 126.9782
                val myPos: LatLng= LatLng.from(latitude, longitude)
            }

        })



    }






}