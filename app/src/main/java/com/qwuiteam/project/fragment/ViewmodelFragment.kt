package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R
import com.qwuiteam.project.databinding.FragmentViewmodelBinding
import com.qwuiteam.project.viewmodel.EditViewModel
import kotlinx.android.synthetic.main.fragment_layout.*

/**
 * id
 */
class ViewmodelFragment : BaseFragment(), TextWatcher {

    override fun getLayoutId(): Int = R.layout.fragment_viewmodel

    lateinit var binding: FragmentViewmodelBinding
    lateinit var editViewModel: EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentViewmodelBinding.inflate(inflater, null, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setTestButton.setOnClickListener {

        }
        editViewModel = ViewModelProvider(this).get("123", EditViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel
        editViewModel.subtitlesLiveData.value = "init 请输入内容"

        binding.edit.doAfterTextChanged {
            if (it.toString().isEmpty()) {
                Log.d("wwq", "为空了: ");
                binding.textView.text = "请输入内容"
            }
        }

        //editViewModel.subtitlesLiveData.postValue("请输入内容")
        //binding.edit2.addTextChangedListener(this)
    }

    override fun onResume() {
        super.onResume()
        //binding.textView.text = "请输入内容"
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        if (s.toString().isEmpty()) {
            binding.textView.text = "请输入内容"
        }else{
            editViewModel.subtitlesLiveData.value = s.toString()
        }
    }
}