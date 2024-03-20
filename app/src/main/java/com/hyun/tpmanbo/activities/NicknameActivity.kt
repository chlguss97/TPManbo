package com.hyun.tpmanbo.activities
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        binding.btn.setOnClickListener {
            val user = auth.currentUser
            user?.let {
                val nickname = binding.etNickname.text.toString()

                val data = hashMapOf(
                    "nickname" to nickname
                )

                // Firebase Firestore에도 닉네임을 저장합니다.
                val docRef = firestore.collection("user").document(user.uid)
                docRef.update(data as Map<String, Any>).addOnSuccessListener {
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, "Firebase에 닉네임을 저장하는 데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }

                // SharedPreferences에 저장된 닉네임을 가져오기
                val savedNickname = sharedPreferences.getString("nickname", null)

                if (savedNickname != null) {
                    // SharedPreferences에 저장된 닉네임이 이미 있는 경우
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // SharedPreferences에 저장된 닉네임이 없는 경우
                    saveNicknameToSharedPreferences(nickname)


                }
            }
        }
    }

    private fun saveNicknameToSharedPreferences(nickname: String) {
        val editor = sharedPreferences.edit()
        editor.putString("nickname", nickname)
        editor.apply()
    }

    private fun navigateToMainActivity() {
        Toast.makeText(this, "닉네임이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}