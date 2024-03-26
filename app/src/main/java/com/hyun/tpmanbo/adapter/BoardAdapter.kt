package com.hyun.tpmanbo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hyun.tpmanbo.data.BoardData
import com.hyun.tpmanbo.databinding.RecyclerBoardFragmentBinding


class BoardAdapter (private val context: Context, val datas: MutableList<BoardData>) : Adapter<BoardAdapter.VH>() {
    inner class VH(val binding: RecyclerBoardFragmentBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater=LayoutInflater.from(context)
        val binding= RecyclerBoardFragmentBinding.inflate(layoutInflater)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentData = datas[position]
        holder.binding.titleArea.text= currentData.title
        holder.binding.contentArea.text= currentData.content
        holder.binding.timeArea.text=currentData.time
        holder.binding.nickname.text=currentData.nickname

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newDataList: List<BoardData>) {
        datas.clear()
        datas.addAll(newDataList)
        notifyDataSetChanged()
    }
}