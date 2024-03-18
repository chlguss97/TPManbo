package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivityLoginEmailBinding
import com.hyun.tpmanbo.databinding.ActivityNicknameBinding

class LoginEmailActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginEmailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignin.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }
    }
}