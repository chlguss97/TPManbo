package com.hyun.tpmanbo.fragment


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.tpmanbo.activities.SettingActivity
import com.hyun.tpmanbo.databinding.FragmentStepBinding
import java.text.SimpleDateFormat

import java.util.Calendar
import java.util.Date
import java.util.Locale


class StepFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentStepBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var stepCountSensor: Sensor
    private var stepCount: Int = 0
    private var lastDate: String = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepBinding.inflate(inflater, container, false)
        return binding.root

        // SensorManager와 Step Counter 센서를 초기화
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) as Sensor

        // 걸음 수 추적에 필요한 권한이 부여되었는지 확인
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            var a = arrayOf(Manifest.permission.ACTIVITY_RECOGNITION)
            requestPermissions(a, 0);
        }

        if (stepCountSensor == null) {
            Toast.makeText(requireContext(), "No Step Detect Sensor", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivSet.setOnClickListener { Toast.makeText(requireContext(), "cal", Toast.LENGTH_SHORT).show()}
//
//        binding.ivCal.setOnClickListener { startActivity(Intent(requireContext(),TestActivity::class.java)) }


        sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL)


        binding.tv.setOnClickListener {
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    var c1 = Calendar.getInstance()
                    var c2 = Calendar.getInstance()
                    c1.set(p1, p2, p3)
                    c2.set(year, month, day)
                    val a = c1.timeInMillis
                    val b = c2.timeInMillis


                    binding.tv.text = "함께보낸 시간들 ${(b - a) / (60 * 60 * 1000 * 24)} 일 ♥"

                }

            }, year, month, day).show()
        }


        binding.ivSet.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    SettingActivity::class.java
                )
            )
        }


    }

    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)


    override fun onSensorChanged(event: SensorEvent?) {
        event.let {
            if (it?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
                stepCount = it.values[0].toInt()
                saveStepCount(stepCount)
            }
        }
    }


    private fun saveStepCount(stepCount: Int) {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        if (currentDate != lastDate) {
            lastDate = currentDate
            val user = auth.currentUser
            user?.let {
                val docRef =
                    firestore.collection("users").document(user.uid).collection("stepCounts")
                        .document(lastDate)
                val data = hashMapOf(
                    "stepCount" to stepCount,
                    "date" to lastDate
                )
                docRef.set(data)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Step count saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            "Failed to save step count",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            TODO("Not yet implemented")
        }
    }


