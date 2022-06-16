package com.haki.mobile.ui.liveroom.imroom.room.widget

import android.content.Context
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hi.dhl.binding.databind
import com.qwuiteam.project.R
import com.qwuiteam.project.databinding.LayoutRoomRedPacketStyleTipBinding

class RoomRedPacketStyleTipLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var nickName: String? = ""
    private val _binding: LayoutRoomRedPacketStyleTipBinding by databind(R.layout.layout_room_red_packet_style_tip)

    init {
        bindData()
    }

    private fun bindData() {
        this.nickName = "12321412"
        Glide.with(this).load("https://picwooyalive.vchat-onlie.com/Fk7Cl9khyZbM1xQ_yeMkBlMY_LTx?imageslim")
            .dontAnimate()
            .transform(CircleCrop())
            .into(_binding.avatarView)
        _binding.textUserName.text = nickName
    }

    fun setTipDescribe(value: String) {
        var nick = _binding.textUserName.text.toString()
        if (nick.length > 6) {
            nick = nick.substring(0, 6) + "..."
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _binding.textSubContent.text =
                Html.fromHtml(
                    StringUtils.getString(R.string.room_lucky_bag_banner, nick, value),
                    Html.FROM_HTML_MODE_LEGACY
                )
        } else {
            _binding.textSubContent.text =
                Html.fromHtml(StringUtils.getString(R.string.room_lucky_bag_banner, nick, value))
        }
    }


}