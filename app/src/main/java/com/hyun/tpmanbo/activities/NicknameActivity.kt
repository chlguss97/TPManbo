package com.hyun.tpmanbo.activities
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.hyun.tpmanbo.G
import com.hyun.tpmanbo.databinding.ActivityNicknameBinding



class NicknameActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityNicknameBinding
    private lateinit var sharedPreferences: SharedPreferences


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





                // 닉네임 중복 검사
                val usersRef = firestore.collection("user")
                usersRef.whereEqualTo("nickname", nickname)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents.isEmpty) {
                            // 닉네임이 중복되지 않는 경우 Firestore에 저장
                            val data = hashMapOf(
                                "nickname" to nickname
                            )


                            sharedPreferences= getSharedPreferences(getString((user.uid).toInt()), Context.MODE_PRIVATE)!!

                            sharedPreferences.edit().putString(nickname, nickname).apply()

                            val docRef = firestore.collection("user").document(user.uid)
                            docRef.set(data, SetOptions.merge()).addOnSuccessListener {
                                Toast.makeText(this, "닉네임이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }.addOnFailureListener { exception ->
                                Toast.makeText(
                                    this,
                                    "Firebase에 닉네임을 저장하는 데 실패하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            // 닉네임이 이미 존재하는 경우 사용자에게 알림
                            Toast.makeText(this, "이미 사용 중인 닉네임입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            this,
                            "닉네임 중복을 확인하는 데 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


            }
        }

    }
}