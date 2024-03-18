package com.hyun.tpmanbo.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyun.tpmanbo.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.LocalDate

import java.util.Date
import androidx.annotation.Nullable as Nullable


class CalendarFragment : Fragment(){

    val binding by lazy { FragmentCalendarBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val date = CalendarDay.today()
        binding.calendarView.setSelectedDate(date)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







    }




    }







