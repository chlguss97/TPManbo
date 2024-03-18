package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSignup.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NicknameActivity::class.java
                )
            )
        }
        binding.toolbar.setOnClickListener { finish() }

        auth = Firebase.auth
        binding.btnSignup.setOnClickListener {
            var email = binding.etId.text.toString()
            var password = binding.etPassword.text.toString()
            var password2 = binding.etPassword2.text.toString()
            if (password != password2){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (email =="" || password=="" || password2=="" ){
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "회원가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            val errorMessage = task.exception?.message ?: "Unknown error"
                            Toast.makeText(this, "회원가입을 실패하였습니다: 이메일이나 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }
}
