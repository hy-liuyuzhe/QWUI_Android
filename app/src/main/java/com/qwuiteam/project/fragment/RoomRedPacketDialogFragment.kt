package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.qwuiteam.project.CommonUtil
import com.qwuiteam.project.R
import com.qwuiteam.project.helper.RoomLuckyRedPacketHelper


/**
 * 房间幸运红包
 */
class RoomRedPacketDialogFragment : BaseCustomDialogFragment() {

    companion object {
        fun newInstance(roomId: String): RoomRedPacketDialogFragment {
            val args = Bundle()
            val fragment = RoomRedPacketDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var isRoomRedPacket = true
    private val colors = intArrayOf(
        ColorUtils.getColor(R.color.color_F9F4D6),
        ColorUtils.getColor(R.color.color_FAC68F)
    )
    private val dp5 = SizeUtils.dp2px(5f).toFloat()
    private val redPacketHelper = RoomLuckyRedPacketHelper()

    override fun setContentLayout(): Int = R.layout.dialog_room_red_packet

//    override fun setWindowAnimations(): Int = R.style.WindowScaleAnimationStyle

    override fun setOnTouchOutSideCancel(): Boolean = true

    override fun setWindowLayout(window: Window?) {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    override fun setGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun initiate() {
        _binding.layoutRedPacketCoin.bindData(
            StringUtils.getString(R.string.gift_expend_gold),
            redPacketHelper.getRoomRedPacketCoin()
        )
        _binding.layoutRedPacketCoin.onClickCallBack = { data ->
            _binding.textCoinTip.text = StringUtils.getString(
                R.string.tv_send_redpackage_handing_fee_tips,
                redPacketHelper.formatCoinTip(data)
            )
            _binding.layoutRedPacketStyleTip.setTipDescribe(data)
        }
        _binding.layoutRedPacketNumber.bindData(
            StringUtils.getString(R.string.quantity), redPacketHelper.getRedPacketNumber()
        )
    }

    override fun setOnListener() {
        _binding.imageClose.setOnClickListener { dismiss() }
        _binding.imageRule.setOnClickListener { switchRuleView() }
        handleSwitchRedPacketTypeListener()
        _binding.textSendRedPacket.setOnClickListener {
//            redPacketHelper.createRedPacket(
//                roomId.orEmpty(),
//                _binding.layoutRedPacketCoin.getData(),
//                _binding.layoutRedPacketNumber.getData(),
//                isRoomRedPacket
//            ) { dismiss() }
        }
    }

    private fun handleSwitchRedPacketTypeListener() {
        val white20 = ColorUtils.getColor(R.color.white_alpha_20)
        val roomLuckyNonSelectedBackground = CommonUtil.createDrawable(white20, dp5, 0f, 0f, dp5)
        val roomLuckySelectedBackground = CommonUtil.createDrawable(colors, dp5, 0f, 0f, dp5)

        val worldLuckyNonSelectedBackground = CommonUtil.createDrawable(white20, 0f, dp5, dp5, 0f)
        val worldLuckySelectedBackground = CommonUtil.createDrawable(colors, 0f, dp5, dp5, 0f)

        val layoutRoomLucky = _binding.layoutRoomLucky
        val layoutWorldLucky = _binding.layoutWorldLucky
        layoutRoomLucky.setOnClickListener {
            isRoomRedPacket = true
            layoutRoomLucky.background = roomLuckySelectedBackground
            layoutWorldLucky.background = worldLuckyNonSelectedBackground
            layoutWorldLucky.setTextColor(ColorUtils.getColor(R.color.white_op_60))
            layoutRoomLucky.setTextColor(ColorUtils.getColor(R.color.color_DE3B21))
            _binding.layoutRedPacketNumber.visibility = View.VISIBLE
            _binding.layoutRedPacketStyleTip.visibility = View.GONE
            _binding.layoutRedPacketCoin.bindData(dataList = redPacketHelper.getRoomRedPacketCoin())
        }
        layoutWorldLucky.setOnClickListener {
            isRoomRedPacket = false
            layoutRoomLucky.background = roomLuckyNonSelectedBackground
            layoutWorldLucky.background = worldLuckySelectedBackground
            layoutWorldLucky.setTextColor(ColorUtils.getColor(R.color.color_DE3B21))
            layoutRoomLucky.setTextColor(ColorUtils.getColor(R.color.white_op_60))
            _binding.layoutRedPacketNumber.visibility = View.GONE
            _binding.layoutRedPacketStyleTip.visibility = View.VISIBLE
            _binding.layoutRedPacketCoin.bindData(dataList = redPacketHelper.getWorldRedPacketCoin())
        }
        layoutRoomLucky.performClick()
    }

    private fun switchRuleView() {
//        RoomRedPacketRuleDialogFragment.newInstance().also {
//            it.show(childFragmentManager, it.javaClass.simpleName)
//        }
    }
}