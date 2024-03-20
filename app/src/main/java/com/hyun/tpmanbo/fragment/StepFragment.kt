package com.hyun.tpmanbo.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.tpmanbo.G

import com.hyun.tpmanbo.activities.SettingActivity
import com.hyun.tpmanbo.databinding.FragmentStepBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class StepFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentStepBinding
    private lateinit var sensorManager: SensorManager
    private var stepCountSensor: Sensor? = null
    private var stepCount: Int = 0
    private var lastDate: String = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val calender = Calendar.getInstance()
    private val year = calender.get(Calendar.YEAR)
    private val month = calender.get(Calendar.MONTH)
    private val day = calender.get(Calendar.DAY_OF_MONTH)
    private var stepCount2: Int = 0
    private lateinit var sharedPreferences: SharedPreferences

    private fun saveDateToSharedPreferences(year: Int, month: Int, day: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("year", year)
        editor.putInt("month", month)
        editor.putInt("day", day)
        editor.apply()
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepBinding.inflate(inflater, container, false)

        // SensorManager와 Step Counter 센서를 초기화
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // 걸음 수 추적에 필요한 권한이 부여되었는지 확인
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val permissions = arrayOf(Manifest.permission.ACTIVITY_RECOGNITION)
            requestPermissions(permissions, 0)
        }

        if (stepCountSensor == null) {
            Toast.makeText(requireContext(), "해당 기종에 만보기 센서가 없습니다.", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // FirebaseAuth 및 FirebaseFirestore 초기화
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // SharedPreferences 초기화
        sharedPreferences = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        return binding.root



    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 센서 등록 해제
        sensorManager.unregisterListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // SharedPreferences에서 데이터 가져오기
        val storedYear = sharedPreferences.getInt("year", -1)
        val storedMonth = sharedPreferences.getInt("month", -1)
        val storedDay = sharedPreferences.getInt("day", -1)
        val storeNickname = sharedPreferences.getString("nickname","No name")

        if (storedYear != -1 && storedMonth != -1 && storedDay != -1) {
            val currentDate = Calendar.getInstance()
            val selectedDate = Calendar.getInstance()
            selectedDate.set(storedYear, storedMonth, storedDay)

            val diffInMillis = currentDate.timeInMillis - selectedDate.timeInMillis
            binding.tvNickname.setText(storeNickname)
            binding.tv.text = "서로만보기와 함께한 지 ${diffInMillis / (60 * 60 * 1000 * 24)} 일 "
        } else {
            // SharedPreferences에 저장된 데이터가 없을 때만 Firestore에서 데이터 가져오기
            val user = auth.currentUser
            user?.let {
                val docRef = firestore.collection("user").document(user.uid)
                docRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val year = document.getLong("year")
                        val month = document.getLong("month")
                        val day = document.getLong("day")


                        if (year != null && month != null && day != null) {
                            val currentDate = Calendar.getInstance()
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(year.toInt(), month.toInt() - 1, day.toInt())

                            val diffInMillis = currentDate.timeInMillis - selectedDate.timeInMillis

                            binding.tv.text = "서로만보기와 함께한 지 ${diffInMillis / (60 * 60 * 1000 * 24)} 일 "
                        } else {
                            Log.d(TAG, "Year, month, or day is null")
                        }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
            }
        }






        binding.tv.setOnClickListener {
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    val selectedYear = p1
                    val selectedMonth = p2
                    val selectedDay = p3

                    // 선택된 날짜를 사용하여 텍스트뷰에 표시
                    val c1 = Calendar.getInstance()
                    val c2 = Calendar.getInstance()
                    c1.set(selectedYear, selectedMonth, selectedDay)
                    c2.set(year, month, day)
                    val a = c1.timeInMillis
                    val b = c2.timeInMillis

                    binding.tv.text = "서로만보기와 함께한 지 ${(b - a) / (60 * 60 * 1000 * 24)} 일 "

                    // SharedPreferences에 선택된 날짜 저장
                    saveDateToSharedPreferences(selectedYear, selectedMonth, selectedDay)

                    // Firestore에 선택된 날짜 업데이트
                    val user = auth.currentUser
                    user?.let {
                        val docRef = firestore.collection("user").document(user.uid)
                        val data = hashMapOf(
                            "year" to selectedYear,
                            "month" to selectedMonth + 1,
                            "day" to selectedDay
                        )
                        docRef.update(data as Map<String, Any>).addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "날짜 변경 성공",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }, year, month, day).show()



        }



        binding.ivSet.setOnClickListener {
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("Start listener", "aaa")
        event?.let {
            if (it.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
                stepCount = it.values[0].toInt()
                //Log.d("stepcount", "$stepCount")
                activity?.runOnUiThread {
                    //Toast.makeText(requireContext(), "$stepCount", Toast.LENGTH_SHORT).show()
                    G.stepcount = stepCount.toString()
//                    binding.tvBalloonCount.text = " ${G.stepcount} 걸음 수 "
                    binding.tvCount.text= "총 ${stepCount} 걸음 수 "
                }
                saveStepCount(stepCount)
            } else {
                //Log.d("StepFragment", "센서타입: ${it.sensor?.type}")
                Toast.makeText(
                    requireContext(),
                    "센서가 존재하지 않습니다 현재 센서: ${it.sensor?.type} ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveStepCount(stepCount: Int) {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // 현재 날짜와 lastDate가 다르다면 lastDate를 업데이트하고 stepCount를 0으로 초기화
        if (lastDate != null && currentDate != lastDate) {
            lastDate = currentDate
            this.stepCount = 0 // 걸음 수 초기화
        }

        val user = auth.currentUser
        user?.let {
            val docRef = firestore.collection("user").document(user.uid)
            val data = hashMapOf(
                "stepCount" to stepCount,
                "date" to lastDate
            )
            docRef.update(data as Map<String, Any>)
//                .addOnSuccessListener {
//                    Toast.makeText(
//                        requireContext(),
//                        "Step count saved successfully",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Failed to save step count",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No implementation needed
    }
}