package com.hyun.tpmanbo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivityMainBinding
import com.hyun.tpmanbo.fragment.CalendarFragment
import com.hyun.tpmanbo.fragment.InfoFragment
import com.hyun.tpmanbo.fragment.MapFragment
import com.hyun.tpmanbo.fragment.StepFragment

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bnv.background= null




        supportFragmentManager.beginTransaction().add(R.id.container_fragment, StepFragment()).commit()

        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_bnv_step -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, StepFragment()).commit()
                R.id.menu_bnv_calender -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, CalendarFragment()).commit()
                R.id.menu_bnv_map -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, MapFragment()).commit()
                R.id.menu_bnv_info -> supportFragmentManager.beginTransaction().replace(R.id.container_fragment, InfoFragment()).commit()
            }
            true
        }

        binding.fab.setOnClickListener { Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show() }




    }

}