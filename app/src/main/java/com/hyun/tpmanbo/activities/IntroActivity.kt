package com.hyun.tpmanbo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import com.bumptech.glide.Glide
import com.hyun.tpmanbo.R
import com.hyun.tpmanbo.databinding.ActivityIntroBinding
import java.util.logging.Handler


class IntroActivity : AppCompatActivity() {

    val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        Glide.with(binding.root).load(R.drawable.intro).into(binding.ivIntro)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },10)




    }
}