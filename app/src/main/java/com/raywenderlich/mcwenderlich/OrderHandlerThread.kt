package com.raywenderlich.mcwenderlich

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import java.util.*

class OrderHandlerThread (private var uiHandler: MainActivity.UiHandler) : HandlerThread("OrderHandlerThread"){

    private var handler: Handler? = null
    private val random = Random ()

    //converting USD to INR
    private fun convertCurrency(foodPriceInDollars: Float): Float {
        return foodPriceInDollars * 68.45f
    }

    private fun attachSideOrder() :String {
        return when (random.nextInt(3)) {
            0 -> "Chips"
            1 -> "Salad"
            else -> "Nachos"

        }
    }
}