package com.hyun.tpmanbo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.tpmanbo.G
import com.hyun.tpmanbo.data.User
import com.hyun.tpmanbo.adapter.UserAdapter
import com.hyun.tpmanbo.databinding.FragmentRankBinding

class RankFragment : Fragment() {

    val userList: MutableList<User> = mutableListOf()
    lateinit var binding: FragmentRankBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchDataFromFirebase()

    }

    private fun fetchDataFromFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("user")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val uid = document.id
                    val nickname = document.getString("nickname") ?: ""
                    val stepCount = document.getLong("stepCount")?.toInt() ?: 0
                    val user = User(uid, nickname,stepCount)
                    userList.add(user)
                    Log.d("FirestoreData", "UID: $uid, Nickname: $nickname, Step Count: $stepCount")
                }
                binding.recyclerView.adapter = UserAdapter(requireContext(), userList)


            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreData", "Error getting documents: ", exception)
                Toast.makeText(requireContext(), "데이터 가져오기 오류", Toast.LENGTH_SHORT).show()
            }



    }

}