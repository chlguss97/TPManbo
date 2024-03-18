package com.hyun.tpmanbo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.toolbar.setOnClickListener{ finish()}
    }
}