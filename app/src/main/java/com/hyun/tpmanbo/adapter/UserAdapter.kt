package com.hyun.tpmanbo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hyun.tpmanbo.G
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.data.User
import com.hyun.tpmanbo.databinding.RecyclerItemListFragmentBinding

class UserAdapter(private val context: Context, private var userList: List<User>) : RecyclerView.Adapter<UserAdapter.VH>() {

    init {
        // userList를 걸음 수(stepCount)에 따라 내림차순으로 정렬
        userList = userList.sortedByDescending { it.stepCount }
    }

    inner class VH(val binding: RecyclerItemListFragmentBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(context)
        val binding = RecyclerItemListFragmentBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentUser = userList[position]
        Log.d("userList","${userList[position].nickname}")
        holder.binding.tvNickname.text= currentUser.nickname
        holder.binding.tvDistance.text= currentUser.stepCount.toString()
        holder.binding.tvSoonwi.text= "${position+1}위"

        // 닉네임이 현재 사용자의 닉네임과 일치하면 배경색을 변경
        if (currentUser.uid == G.uid) {
            holder.binding.layoutCardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue))
        } else {
            holder.binding.layoutCardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.whitepink))
        }

        Log.d("current","${G.uid},${currentUser.uid}")

    }
}