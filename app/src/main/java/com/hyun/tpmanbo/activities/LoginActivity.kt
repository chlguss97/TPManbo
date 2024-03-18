package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvGo.setOnClickListener { startActivity(Intent(this,MainActivity::class.java))
                                            finish()}
        binding.layoutSignup.setOnClickListener { startActivity(Intent(this,SignupActivity::class.java)) }
        binding.layoutEmailLogin.setOnClickListener { startActivity(Intent(this, LoginEmailActivity::class.java)) }




    }
}