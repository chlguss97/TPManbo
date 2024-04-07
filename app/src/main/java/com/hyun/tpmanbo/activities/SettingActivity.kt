package com.hyun.tpmanbo.activities

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.tpmanbo.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    val db= FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvNickname.text="닉네임 없음"
        binding.civProfile.setOnClickListener { Toast.makeText(this, "사진 변경", Toast.LENGTH_SHORT).show() }
        binding.ivChangeNickname.setOnClickListener { Toast.makeText(this, "닉네임변경", Toast.LENGTH_SHORT).show() }
        binding.btnLogout.setOnClickListener { Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show() }

        val user = auth.currentUser
        user?.let {
            val docRef = db.collection("user").document(user.uid)
            docRef.get().addOnSuccessListener { document->
                if(document.exists()){
                    val nickname = document.getString("nickname")
                    binding.tvNickname.text= nickname
                }


            }
        }?.addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "get failed with ", exception)
        }



        binding.toolbar.setOnClickListener{ finish()}
        binding.tvNickname.text
    }
}