package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore

import com.hyun.tpmanbo.databinding.ActivityLoginEmailBinding


class LoginEmailActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignin.setOnClickListener {
            val email = binding.etId.text.toString().trim() // Firebase에 저장된 이메일 주소
            val password = binding.etPassword.text.toString().trim()

            if(email != "" && password != "" ){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인을 성공하였습니다", Toast.LENGTH_SHORT).show()

                        val user = auth.currentUser
                        user?.let {
                            val firestore = FirebaseFirestore.getInstance()
                            val docRef = firestore.collection("user").document(user.uid)

                            docRef.get().addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val nickname = document.getString("nickname")
                                    if (nickname != null) {
                                        // 닉네임이 존재하는 경우 MainActivity로 이동
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    } else {
                                        // 닉네임이 존재하지 않는 경우 닉네임 액티비티로 이동
                                        startActivity(Intent(this, NicknameActivity::class.java))
                                        finish()
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "로그인에 실패하였습니다. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }


                }


            }else{
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}