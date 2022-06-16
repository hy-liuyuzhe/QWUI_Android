package com.qwuiteam.project.view
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.hi.dhl.binding.databind
import com.qwuiteam.project.CommonUtil
import com.qwuiteam.project.R
import com.qwuiteam.project.databinding.LayoutRoomRedPacketTypeBinding

class RoomRedPacketTypeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), View.OnClickListener {

    private val _binding: LayoutRoomRedPacketTypeBinding by databind(R.layout.layout_room_red_packet_type)

    private var dataList: List<String>? = null
    private var selectedIndex = 0
    private val dp4 = SizeUtils.dp2px(4f).toFloat()
    private val isArabic = false
    private val colors = intArrayOf(
        if (isArabic) CommonUtil.getColor(R.color.color_FAC68F) else CommonUtil.getColor(R.color.color_F9F4D6),
        if (isArabic) CommonUtil.getColor(R.color.color_F9F4D6) else CommonUtil.getColor(R.color.color_FAC68F)
    )
    private val unSelectColor = ColorUtils.getColor(R.color.color_FAC792)
    private val selectColor = ColorUtils.getColor(R.color.color_DE3B21)
    private val type1Drawable = CommonUtil.createDrawable(colors, dp4, 0f, 0f, dp4)
    private val type2Drawable = CommonUtil.createDrawable(colors, 0f)
    private val type3Drawable = CommonUtil.createDrawable(colors, 0f)
    private val type4Drawable = CommonUtil.createDrawable(colors, 0f, dp4, dp4, 0f)
    private var data: String? = null
    var onClickCallBack: ((data: String) -> Unit)? = null
        set(value) {
            field = value
            _binding.textType1.performClick()
        }

    init {
        _binding.run {
            textType1.setOnClickListener(this@RoomRedPacketTypeLayout)
            textType2.setOnClickListener(this@RoomRedPacketTypeLayout)
            textType3.setOnClickListener(this@RoomRedPacketTypeLayout)
            textType4.setOnClickListener(this@RoomRedPacketTypeLayout)
        }
        _binding.textType1.performClick()
    }

    fun bindData(title: String = "", dataList: List<String>?) {
        if (dataList.isNullOrEmpty() || dataList.size < 4) return
        this.dataList = dataList
        if (!TextUtils.isEmpty(title)) _binding.textRedPacketTitle.text = title
        this.dataList?.apply {
            takeIf { it.size > 0 }?.also { _binding.textType1.text = it[0] }
            takeIf { it.size > 1 }?.also { _binding.textType2.text = it[1] }
            takeIf { it.size > 2 }?.also { _binding.textType3.text = it[2] }
            takeIf { it.size > 3 }?.also { _binding.textType4.text = it[3] }
        }
        this.data = dataList[selectedIndex]
        onClickCallBack?.invoke(getData())
    }

    private fun reset() {
        _binding.apply {
            reset(textType1)
            reset(textType2)
            reset(textType3)
            reset(textType4)
        }
    }


    private fun reset(target: TextView) {
        target.background = null
        target.setTextColor(unSelectColor)
    }

    override fun onClick(v: View) {
        reset()
        when (v.id) {
            R.id.textType1 -> {
                v.background = type1Drawable
                selectedIndex = 0
            }
            R.id.textType2 -> {
                v.background = type2Drawable
                selectedIndex = 1
            }
            R.id.textType3 -> {
                v.background = type3Drawable
                selectedIndex = 2
            }
            R.id.textType4 -> {
                v.background = type4Drawable
                selectedIndex = 3
            }
        }
        (v as TextView).setTextColor(selectColor)
        data = dataList?.get(selectedIndex)
        onClickCallBack?.invoke(getData())
    }

    fun getData(): String {
        return data.orEmpty()
    }
}