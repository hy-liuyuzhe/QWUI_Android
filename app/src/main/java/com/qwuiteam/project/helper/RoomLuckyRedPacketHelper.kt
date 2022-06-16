package com.qwuiteam.project.helper

import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R

class RoomLuckyRedPacketHelper {

//    private val roomModel = RoomLuckyModel()
//    private val appConfig = DemoCache.getAppConfig()


    fun createRedPacket(
        roomId: String,
        totalGold: String,
        bagNum: String,
        isRoomBag: Boolean,
        success: () -> Unit
    ) {
        if (roomId.isEmpty()
            || bagNum.isEmpty()
            || totalGold.isEmpty()
        ) {
            return
        }
//        roomModel.createRoomLuckyBag(
//            roomId,
//            totalGold,
//            bagNum,
//            isRoomBag,
//            object : HttpRequestCallBack<Any?>() {
//                override fun onSuccess(message: String?, response: Any?) {
//                    success.invoke()
//                }
//
//                override fun onFailure(code: Int, msg: String?) {
//                    if (msg.isNullOrEmpty()) return
//                    toast(msg)
//                }
//            })
    }

    fun getRedPacketNumber(): List<String> {
//        val appConfig = DemoCache.getAppConfig()
//        if (appConfig?.luckyBagNum?.isNotEmpty() == true) {
//            return appConfig.luckyBagNum
//        }
        return Utils.getApp().resources.getStringArray(R.array.luckyBagNum)
            .asList()
    }

    fun getRoomRedPacketCoin(): List<String> {
//        if (appConfig?.roomLuckyBagGolds?.isNotEmpty() == true) {
//            return appConfig.roomLuckyBagGolds
//        }
        return Utils.getApp().resources.getStringArray(R.array.roomLuckyBagGolds)
            .asList()
    }

    fun getWorldRedPacketCoin(): List<String> {
//        val appConfig = DemoCache.getAppConfig()
//        if (appConfig?.allRoomLuckyBagGolds?.isNotEmpty() == true) {
//            return appConfig.allRoomLuckyBagGolds
//        }
        return Utils.getApp().resources.getStringArray(R.array.allRoomLuckyBagGolds)
            .asList()
    }

    fun formatCoinTip(roomLuckyGold: String): Int {
        return ((roomLuckyGold.toDouble() * (1 - 0.05)).toInt())
    }
}