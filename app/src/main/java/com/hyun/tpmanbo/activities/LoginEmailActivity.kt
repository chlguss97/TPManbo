package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hyun.tpmanbo.G
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.User
import com.hyun.tpmanbo.databinding.ActivityLoginEmailBinding
import com.hyun.tpmanbo.databinding.ActivityNicknameBinding
import com.hyun.tpmanbo.databinding.ActivitySignupBinding

class LoginEmailActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth


    val binding by lazy { ActivityLoginEmailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.btnSignin.setOnClickListener {
            val email = binding.etId.text.toString() // Firebase에 저장된 이메일 주소
            val password = binding.etPassword.text.toString() // Firebase에 저장된 비밀번호

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    Toast.makeText(this, "로그인을 성공하였습니다", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,NicknameActivity::class.java))
                    G.userAccount= User(email,password)
                    finish()
                }else(Toast.makeText(this, "로그인에 실패하였습니다. ${task.exception?.message}", Toast.LENGTH_SHORT).show())
            }
        }



        binding.toolbar.setOnClickListener{ finish()}
    }
}