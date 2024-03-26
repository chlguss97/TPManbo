package com.hyun.tpmanbo.activities
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        binding.toolbar.setOnClickListener { finish() }


        binding.btn.setOnClickListener {
            val user = auth.currentUser
            user?.let {
                val nickname = binding.etNickname.text.toString()

                val data = hashMapOf(
                    "nickname" to nickname
                )

                // Firebase Firestore 닉네임을 저장합니다.
                val docRef = firestore.collection("user").document(user.uid)
                docRef.update(data as Map<String, Any>).addOnSuccessListener {
                    Toast.makeText(this, "닉네임이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, "Firebase에 닉네임을 저장하는 데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }





    }
