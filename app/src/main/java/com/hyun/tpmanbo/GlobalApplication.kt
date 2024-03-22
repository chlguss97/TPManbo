package com.hyun.tpmanbo

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK를 초기화합니다. 여기서는 Kakao 개발자 콘솔에서 발급받은 API 키를 사용합니다.
        KakaoSdk.init(this, "c9af3d8cceae7b2828f18c6a194f6a90")
    }
}