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

    //invoked before Looper starts looping the message
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = getHandler(looper)
    }

    private fun getHandler(looper: Looper): Handler {
        //1//creating and returning a handler object and passes the looper through its
        //    //parameters
        return object:Handler(looper) {
            //2 handles the upcoming order
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                //3 gets order from handler
                val foodOrder = msg?.obj as FoodOrder
                //4 calls converter
                foodOrder.foodPrice = convertCurrency(foodOrder.foodPrice)
                //5 add side dishes to the order
                foodOrder.sideOrder = attachSideOrder()
                //6 creates message to wrap the order
                val processedMessage = Message()
                //7 puts processed foodOrder in message
                processedMessage.obj = foodOrder
                //8 send message to the UIhandler
                uiHandler.sendMessage(processedMessage)
            }

        }
    }
    fun sendOrder(foodOrder: FoodOrder) {
        //1 creates new message object
        val message = Message()
        //2 wrap order to the message
        message.obj = foodOrder
        //3 sends message to the handler
        handler?.sendMessage(message)
    }

}