package com.hyun.tpmanbo.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.tpmanbo.databinding.ActivityNicknameBinding
import com.hyun.tpmanbo.activities.MainActivity

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

        binding.btnStart.setOnClickListener {
            val user = auth.currentUser
            user?.let {
                val docRef = firestore.collection("user").document(it.uid)
                docRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Firestore에 닉네임이 이미 있는 경우
                        val nickname = document.getString("nickname")
                        if (nickname != null) {
                            // 닉네임이 존재하는 경우 MainActivity로 이동
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    } else {
                        // Firestore에 문서가 존재하지 않는 경우
                        val nicknameFromEditText = binding.etNickname.text.toString()
                        Log.d("aaa","닉네임: $nicknameFromEditText")
                        val data = hashMapOf(
                            "nickname" to nicknameFromEditText
                        )
                        docRef.set(data)
                            .addOnSuccessListener {
                                // 닉네임 저장 성공 시 MainActivity로 이동
                                Toast.makeText(this, "닉네임 저장에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { exception ->
                                // 닉네임 저장 실패 시 오류 메시지 표시
                                Log.e(TAG, "Error updating nickname", exception)
                                Toast.makeText(this, "닉네임 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                            }
                    }
                }.addOnFailureListener { exception ->
                    // Firestore 문서를 가져오는 데 실패한 경우
                    Log.e(TAG, "Error getting document", exception)
                    Toast.makeText(this, "문서 가져오기에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}