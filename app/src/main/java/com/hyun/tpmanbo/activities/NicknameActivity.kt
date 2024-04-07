package com.hyun.tpmanbo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.hyun.tpmanbo.databinding.ActivityNicknameBinding

class NicknameActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // 툴바 클릭 시 액티비티 종료
        binding.toolbar.setOnClickListener { finish() }

        // 버튼 클릭 시 닉네임 저장 시도
        binding.btn.setOnClickListener { saveNickname() }
    }

    private fun saveNickname() {
        val user = auth.currentUser
        user?.let {
            val nickname = binding.etNickname.text.toString().trim() // 입력한 닉네임 앞뒤 공백 제거

            // 닉네임 중복 검사
            val usersRef = firestore.collection("user")
            usersRef.whereEqualTo("nickname", nickname)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        // 닉네임이 중복되지 않는 경우 Firestore에 저장
                        val data = hashMapOf("nickname" to nickname)
                        val docRef = firestore.collection("user").document(user.uid)
                        docRef.set(data, SetOptions.merge())
                            .addOnSuccessListener {
                                showToast("닉네임이 저장되었습니다.")
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { showToast("Firebase에 닉네임을 저장하는 데 실패하였습니다.") }
                    } else {
                        showToast("이미 사용 중인 닉네임입니다.")
                    }
                }
                .addOnFailureListener { showToast("닉네임 중복을 확인하는 데 실패하였습니다.") }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}