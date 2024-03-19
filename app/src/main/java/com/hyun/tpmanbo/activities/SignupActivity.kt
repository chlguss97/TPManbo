package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val firestore = Firebase.firestore
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
            val email = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val password2 = binding.etPassword2.text.toString()

            if (password != password2){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


                            // 사용 가능한 이메일인 경우 회원가입 진행
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        // 회원가입 성공
                                        val user = auth.currentUser
                                        val userDocRef = firestore.collection("user").document("${user?.uid}")
                                        val userData = hashMapOf(
                                            "email" to email,
                                            "password" to password
                                        )
                                        userDocRef.set(userData)
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "Firebase에 사용자 정보를 저장하는 데 성공했습니다.", Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this, MainActivity::class.java))
                                                finish()
                                            }
                                            .addOnFailureListener { exception ->
                                                Toast.makeText(this, "Firestore에 사용자 정보를 저장하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                            }

                                    }else {
                                        // 회원가입 실패
                                        val errorMessage = task.exception?.message ?: "회원가입 실패"
                                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                                    }

                                }


        }
    }
}

