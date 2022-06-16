package com.qwuiteam.project.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.qwuiteam.project.R;
import com.qwuiteam.project.databinding.DialogRoomRedPacketBinding;

/**
 * 解决AppCompatDialogFragment show方法的异常问题
 * <p>
 * 如果需要加载数据，能够显示各种状态的dialogFragment
 *
 * @author dell
 */
public abstract class BaseCustomDialogFragment extends AppCompatDialogFragment {

    protected Context mContext;
    DialogRoomRedPacketBinding _binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CoreManager.addClient(this);
        if (getArguments() != null) {
            initArguments(getArguments());
        }
    }

    protected void initArguments(Bundle arguments) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        _binding = DataBindingUtil.inflate(inflater, setContentLayout(), window.findViewById(android.R.id.content), false);
        setCancelable(setOnTouchOutSideCancel());
        initWindowLayout(window);
        return _binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initiateView(view);
        setOnListener();
        initiate();
    }


    protected void initiateView(View view) {

    }

    protected void setOnListener() {

    }

    protected void initiate() {

    }

    /**
     * 初始化布局
     */
    protected int setContentLayout() {
        return 0;
    }

    /**
     * 初始化布局
     */
    protected View setContentView() {
        return null;
    }

    private void initWindowLayout(Window window) {
        window.setWindowAnimations(setWindowAnimations());
        window.setBackgroundDrawable(new ColorDrawable(setBackgroundColorResources()));
        window.setGravity(setGravity());
        setWindowLayout(window);
    }

    protected @ColorInt
    int setBackgroundColorResources() {
        return Color.TRANSPARENT;
    }

    protected int setWindowAnimations() {
        return R.style.ErbanUserInfoDialog;
    }

    protected void setWindowLayout(Window window) {
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置点击外部是否可以取消
     */
    protected boolean setOnTouchOutSideCancel() {
        return false;
    }

    /**
     * 默认中心显示
     */
    protected int setGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(this, tag);
        //这里要改为commitAllowingStateLoss，防止页面onSaveInstanceState（后台或者销毁）后，
        // 抛IllegalStateException:Can not perform this action after onSaveInstanceState异常
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void dismiss() {
        try {
            //这里要改为dismissAllowingStateLoss，防止页面onSaveInstanceState（后台或者销毁）后，
            // 抛IllegalStateException:Can not perform this action after onSaveInstanceState异常
            super.dismissAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
