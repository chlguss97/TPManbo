package com.hyun.tpmanbo.fragment


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.hyun.tpmanbo.activities.SettingActivity
import com.hyun.tpmanbo.databinding.FragmentStepBinding
import java.util.Calendar

class StepFragment : Fragment() {

    lateinit var binding: FragmentStepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStepBinding.inflate(inflater,container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivSet.setOnClickListener { Toast.makeText(requireContext(), "cal", Toast.LENGTH_SHORT).show()}
//
//        binding.ivCal.setOnClickListener { startActivity(Intent(requireContext(),TestActivity::class.java)) }

        binding.tv.setOnClickListener { DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                var c1 = Calendar.getInstance()
                var c2 = Calendar.getInstance()
                c1.set(p1, p2, p3)
                c2.set(year,month,day)
                val a =c1.timeInMillis
                val b =c2.timeInMillis


                binding.tv.text= "함께보낸 시간들 ${(b-a)/(60*60*1000*24)} 일 ♥"

            }

        },year,month,day).show() }


        binding.ivSet.setOnClickListener { startActivity(Intent(requireContext(),SettingActivity::class.java)) }



    }






    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)
}

