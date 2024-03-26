package com.hyun.tpmanbo.activities

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.hyun.tpmanbo.databinding.ActivityLoginBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvGo.setOnClickListener { startActivity(Intent(this,MainActivity::class.java))
            finish()}
        binding.layoutSignup.setOnClickListener { startActivity(Intent(this,SignupActivity::class.java)) }
        binding.layoutEmailLogin.setOnClickListener { startActivity(Intent(this, LoginEmailActivity::class.java)) }

        binding.civGoogle.setOnClickListener { Toast.makeText(this, "구글로그인", Toast.LENGTH_SHORT).show() }
        binding.civKakao.setOnClickListener { Toast.makeText(this, "카카오로그인", Toast.LENGTH_SHORT).show() }
        binding.civNaver.setOnClickListener { Toast.makeText(this, "네이버로그인", Toast.LENGTH_SHORT).show() }

        getHashKey()
    }
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}