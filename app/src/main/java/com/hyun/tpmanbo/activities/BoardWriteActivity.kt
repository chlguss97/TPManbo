package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.tpmanbo.G
import com.hyun.tpmanbo.adapter.BoardAdapter
import com.hyun.tpmanbo.data.BoardData
import com.hyun.tpmanbo.databinding.ActivityBoardWriteBinding
import com.hyun.tpmanbo.fragment.BoardFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BoardWriteActivity : AppCompatActivity() {
    val binding by lazy { ActivityBoardWriteBinding.inflate(layoutInflater) }
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val time = getTime()
            val nickname= G.nickname

            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid
            if (userId != null) {
                db.collection("user").document(userId)
                    .collection("board") // user 컬렉션 내에 board 서브컬렉션을 만듭니다.
                    .add(BoardData(title, content, time, nickname))
                    .addOnSuccessListener {
                        Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "게시글 입력 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "사용자 인증되지 않음", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 시간을 구하는 함수
    fun getTime(): String {
        val currentDateTime = Calendar.getInstance().time
        val dateFormat =
            SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

        return dateFormat
    }




}
