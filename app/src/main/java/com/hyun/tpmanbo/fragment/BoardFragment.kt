package com.hyun.tpmanbo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.tpmanbo.activities.BoardWriteActivity
import com.hyun.tpmanbo.adapter.BoardAdapter
import com.hyun.tpmanbo.data.BoardData

import com.hyun.tpmanbo.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding
    val datas: MutableList<BoardData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDelete.setOnClickListener { Toast.makeText(requireContext(), "글삭제", Toast.LENGTH_SHORT).show() }
        binding.btnEdit.setOnClickListener { startActivity(Intent(requireContext(), BoardWriteActivity::class.java)) }

        // Firestore에서 데이터 가져오기
        getFirestoreData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getFirestoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("user")
            .get()
            .addOnSuccessListener { userResult ->
                for (userDocument in userResult) {
                    val userId = userDocument.id // 각 사용자 문서의 ID (UID)

                    // 사용자 문서의 board 서브컬렉션에 대한 참조 가져오기
                    val userBoardCollectionRef = db.collection("user").document(userId).collection("board")

                    // 해당 서브컬렉션의 데이터 가져오기
                    userBoardCollectionRef.get()
                        .addOnSuccessListener { boardResult ->
                            for (boardDocument in boardResult) {
                                val title = boardDocument.getString("title") ?: ""
                                val content = boardDocument.getString("content") ?: ""
                                val time = boardDocument.getString("time") ?: ""
                                val nickname = userDocument.getString("nickname") ?: "이름 없음" // 게시글 작성자의 닉네임

                                val data = BoardData(title, content, time, nickname)
                                datas.add(data)
                                Log.d("aaa", "${title}, ${content}, ${time}, ${nickname}")
                            }

                            // 어댑터에 데이터 설정 및 갱신
                            binding.recyclerView.adapter = BoardAdapter(requireContext(), datas)
                            binding.recyclerView.adapter?.notifyDataSetChanged()
                        }
                        .addOnFailureListener { exception ->
                            Log.d("FirestoreData", "Error getting board documents: ", exception)
                            Toast.makeText(requireContext(), "게시글 데이터 가져오기 오류", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreData", "Error getting user documents: ", exception)
                Toast.makeText(requireContext(), "사용자 데이터 가져오기 오류", Toast.LENGTH_SHORT).show()
            }
    }
}