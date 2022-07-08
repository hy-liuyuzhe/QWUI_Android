package com.qwuiteam.project.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pk_dialog.*
import kotlinx.android.synthetic.main.fragment_pk_dialog.recyclerView

class RoomPkBottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**zune: 设置全屏模式，防止周围留白 */
        setStyle(STYLE_NORMAL, R.style.Theme_Dialog_NoTitle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pk_dialog, null, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.peekHeight = SizeUtils.dp2px(300f)
        val window = dialog.window!!
//        val lp = window!!.attributes
//        window.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT
//        );
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        lp.gravity = Gravity.BOTTOM
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT
//        window.attributes = lp
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = PageContainer.values().toMutableList()
        data.addAll(data)
        data.addAll(data)
        data.addAll(data)
        data.addAll(data)
        data.addAll(data)
        recyclerView.adapter =
            object :
                BaseQuickAdapter<PageContainer, BaseViewHolder>(
                    R.layout.item_main,data
                ) {
                override fun convert(holder: BaseViewHolder, item: PageContainer) {
                    holder.setText(R.id.itemText, item.title)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }


}