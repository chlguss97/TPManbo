package com.hyun.tpmanbo.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.activities.MainActivity
import com.hyun.tpmanbo.databinding.FragmentMapBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdate
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions



class MapFragment : Fragment() {










    private lateinit var binding: FragmentMapBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val mapview : MapView = binding.mapView
        mapview.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                Toast.makeText(requireContext(), "지도를 정상적으로 불러왔습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onMapError(error: Exception) {
                Toast.makeText(requireContext(), "지도를 불러오지 못했습니다${error}", Toast.LENGTH_SHORT).show()
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 현재 내 위치를 지도의 중심위치로 설정
                val latitude: Double = (activity as MainActivity).myLocation?.latitude ?: 37.5666
                val longitude: Double =  (activity as MainActivity).myLocation?.longitude ?: 126.9782
                Toast.makeText(requireContext(), "${latitude} ${longitude}", Toast.LENGTH_SHORT).show()
                val myPos: LatLng = LatLng.from(latitude, longitude)

                // 내 위치로 지도 카메라 이동
                val cameraUpdate: CameraUpdate = CameraUpdateFactory.newCenterPosition(myPos, )
                kakaoMap.moveCamera(cameraUpdate)

                // 내 위치 마커 추가하기
                val labelOption: LabelOptions = LabelOptions.from(myPos).setStyles(R.drawable.locationmark)
                //라벨이 그려질 레이어 객체 소환
                val labelLayer: LabelLayer = kakaoMap.labelManager!!.layer!!
                //라벨 레이어에 추가
                labelLayer.addLabel(labelOption)

                // 인증 후 API 가 정상적으로 실행될 때 호출됨
            }
        })



    }
}