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
import com.hyun.tpmanbo.R
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

        val user = auth.currentUser
        user?.let {
            val docRef = firestore.collection("user").document(it.uid)
            docRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val nickname = document.getString("nickname")
                    if (nickname != null) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else {
                        // 닉네임이 없는 경우
                        val nicknameFromEditText = binding.etNickname.text.toString()
                        val data = hashMapOf(
                            "nickname" to nicknameFromEditText)
                        docRef.update(data as Map<String, Any>)}sss
                }
            }.addOnFailureListener { exception ->
                Log.e(TAG, "Error getting document", exception)
                Toast.makeText(this, "문서 가져오기에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}