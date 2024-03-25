package com.hyun.tpmanbo.data

data class User(
    var uid : String = "",
    var nickname : String= "",
    var stepCount : Int = 0,
    var email: String="",
    var password: String=""
)
