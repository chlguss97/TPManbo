package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivityNicknameBinding

class NicknameActivity : AppCompatActivity() {

    val binding by lazy { ActivityNicknameBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnStart.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }
        binding.toolbar.setOnClickListener{ finish()}
    }
}