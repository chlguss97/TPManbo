package com.hyun.tpmanbo.activities


import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivityMainBinding
import com.hyun.tpmanbo.fragment.BoardFragment
import com.hyun.tpmanbo.fragment.RankFragment
import com.hyun.tpmanbo.fragment.MapFragment
import com.hyun.tpmanbo.fragment.StepFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var myLocation: Location?= null
    // [ Google Fused Location API 사용 : play-services-location ]
    val locationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    // kakao search API 응답결과 객체 참조변수


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.fab.setOnClickListener { }




        supportFragmentManager.beginTransaction().add(R.id.container_fragment, StepFragment()).commit()

        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_bnv_step -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, StepFragment()).commit()
                R.id.menu_bnv_calender -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, RankFragment()).commit()
                R.id.menu_bnv_map -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, MapFragment()).commit()
                R.id.menu_bnv_info -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, BoardFragment()).commit()
            }

            true
        }


        val permissionState: Int = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionState==PackageManager.PERMISSION_DENIED){
            permissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            requestMyLocation()
        }



        }






    val permissionResultLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        if(it) requestMyLocation()
        else Toast.makeText(this, "내 위치정보를 제공하지 않아 검색기능 사용이 제한됩니다.", Toast.LENGTH_SHORT).show()
    }
    private fun requestMyLocation(){

        //요청 객체 생성
        val request: LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000).build()

        //실시간 위치정보 갱신 요청 - 퍼미션 체크코드가 있어야만 함.
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationProviderClient.requestLocationUpdates(request,locationCallback, Looper.getMainLooper())
    }

    private val locationCallback= object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            myLocation= p0.lastLocation

            //위치 탐색이 종료되었으니 내 위치 정보 업데이트를 이제 그만...
            locationProviderClient.removeLocationUpdates(this) //this: locationCallback 객체

        }
    }


//    //카카오 로컬 검색 API를 활용하여 키워드로 장소를 검색하는 기능 메소드
//    private fun searchPlaces(){
//        Toast.makeText(this, "$searchQuery\n${myLocation?.latitude},${myLocation?.longitude}", Toast.LENGTH_SHORT).show()
//
//        // 레트로핏을 이용한 REST API 작업 수행 - GET방식
//        val retrofit= RetrofitHelper.getRetrofitInstance("https://dapi.kakao.com")
//        val retrofitApiService= retrofit.create(RetrofitApiService::class.java)
//        val call= retrofitApiService.searchPlace(searchQuery,myLocation?.longitude.toString(), myLocation?.latitude.toString())
//        call.enqueue(object : Callback<KakaoSearchPlaceResponce> {
//            override fun onResponse(
//                call: Call<KakaoSearchPlaceResponce>,
//                response: Response<KakaoSearchPlaceResponce>
//            ) {
//                //응답받은 json을 파싱한 객체를 참조하기
//                searchPlaceResponce= response.body()
//
//                //먼저 데이터가 온전히 잘 왔는지 파악해보기
//                val meta: PlaceMeta? = searchPlaceResponce?.meta
//                val document: List<Place>? = searchPlaceResponce?.documents
//
//                //AlertDialog.Builder(this@MainActivity).setMessage("${meta?.total_count}\n${document?.get(0)?.place_name}").create().show()
//
//                //무조건 검색이 완료되면 '리스트 형태로 먼저 보여주도록 할 것임.
//                binding.bnv.selectedItemId= R.id.menu_bnv_list
//
//                //fab 버튼 원위치
//                ObjectAnimator.ofFloat(binding.fabRefresh,"translationY", 0f).start()
//                ObjectAnimator.ofFloat(binding.fabRefresh,"rotationX", 0f).start()
//
//            }
//
//            override fun onFailure(call: Call<KakaoSearchPlaceResponce>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "서버 오류가 있습니다.", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
////        val call= retrofitApiService.searchPlaceToString(searchQuery, myLocation?.longitude.toString(), myLocation?.latitude.toString())
////        call.enqueue(object : Callback<String>{
////            override fun onResponse(call: Call<String>, response: Response<String>) {
////                val s= response.body()
////                AlertDialog.Builder(this@MainActivity).setMessage(s).create().show()
////            }
////
////            override fun onFailure(call: Call<String>, t: Throwable) {
////                Toast.makeText(this@MainActivity, "error : ${t.message}", Toast.LENGTH_SHORT).show()
////            }
////        })
//
//
//    }




    }






