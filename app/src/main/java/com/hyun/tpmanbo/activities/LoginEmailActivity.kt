package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivityLoginEmailBinding
import com.hyun.tpmanbo.databinding.ActivityNicknameBinding
import com.hyun.tpmanbo.databinding.ActivitySignupBinding

class LoginEmailActivity : AppCompatActivity() {




    val binding by lazy { ActivityLoginEmailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)





        binding.btnSignin.setOnClickListener { startActivity(Intent(this,NicknameActivity::class.java)) }
        binding.toolbar.setOnClickListener{ finish()}
    }
}