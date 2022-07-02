package com.qwuiteam.project.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;


/**
 * 裁剪视频
 *
 * @author lyz
 */
public class CropVideoCursorLayout extends LinearLayout {
    private static final String TAG = "CropVideoCursorLayout";

    private int touchAreaWidth = 60;
    private int dragAreaDirection = 0;
    private static final int LEFT = 0x16;
    private static final int RIGHT = 0x18;
    private static final int CENTER = 0x19;
    private int mTouchMode;
    private static final int TOUCH_MODE_IDLE = 0;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private boolean isIntercept = true;
    private boolean scrollState = false;
    private int appScreenWidth;

    private int oriLeft = 0;
    private int oriRight = 0;


    private int mTouchSlop = 8;
    private float mTouchX;
    private int minWidth;
    private int lastRaX;
    boolean disableTouch = false;
    private int viewWidth;

    public CropVideoCursorLayout(@NonNull Context context) {
        super(context);
    }

    public CropVideoCursorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropVideoCursorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchAreaWidth = SizeUtils.dp2px(32);
        minWidth = SizeUtils.dp2px(1);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        appScreenWidth = ScreenUtils.getAppScreenWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = getWidth();
        Log.d("liuyuzhe", "onSizeChanged: "+viewWidth);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int dragDirection = getDirection(ev.getX());
        isIntercept = false;
        if (dragDirection != CENTER) {
            isIntercept = true;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isIntercept) {
            return super.onTouchEvent(ev);
        }

        if (disableTouch) {
            return super.onTouchEvent(ev);
        }

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                mTouchMode = TOUCH_MODE_DOWN;
                mTouchX = x;
                dragAreaDirection = getDirection(ev.getX());
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                switch (mTouchMode) {
                    case TOUCH_MODE_IDLE:
                        break;
                    case TOUCH_MODE_DOWN: {
                        final float x = ev.getX();
                        Log.d(TAG, "onTouchEvent:ACTION_MOVE " + Math.abs(x - mTouchX));
                        if (Math.abs(x - mTouchX) > mTouchSlop) {
                            mTouchMode = TOUCH_MODE_DRAGGING;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            mTouchX = x;
                            lastRaX = (int) ev.getRawX();
                            oriLeft = getLeft();
                            oriRight = getRight();// - ViewUtils.dpToPx(32);
                            return true;
                        }
                        break;
                    }

                    case TOUCH_MODE_DRAGGING: {
                        int tempRawX = (int) ev.getRawX();
                        int dx = tempRawX - lastRaX;
                        lastRaX = tempRawX;
                        Log.d("liuyuzhe", "onTouchEvent: " + tempRawX);
                        fixLayout(dx, ev);
                        return true;
                    }
                    default:
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (mTouchMode == TOUCH_MODE_DRAGGING) {
                    mTouchMode = TOUCH_MODE_IDLE;
                    scrollState = false;
                    if (cropVideoListener != null) cropVideoListener.onUp(dragAreaDirection);
                    super.onTouchEvent(ev);
                    return true;
                }
                mTouchMode = TOUCH_MODE_IDLE;
                break;
            }
            default:
        }

        return true;
    }

    public long getLeftMargin() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        return layoutParams.leftMargin;
    }

    @SuppressLint("NewApi")
    private void fixLayout(int scrollOffset, MotionEvent ev) {
        Log.d(TAG, "fixLayout: ------" + scrollOffset);
        switch (dragAreaDirection) {
            case LEFT:
                oriLeft = oriLeft + scrollOffset;
                float rawX = ev.getRawX();
                if (rawX > appScreenWidth - touchAreaWidth && !scrollState) {
                    Log.d("liuyuzhe", "fixLayout: 开始滑动");
                    if (cropVideoListener != null && !scrollState) {
                        scrollState = true;
                        cropVideoListener.onScrollScreenLeftEdge(true);
                    }
                    return;
                } else if (scrollState && rawX < appScreenWidth - touchAreaWidth) {
                    Log.d("liuyuzhe", "fixLayout: 停止滑动");
                    scrollState = false;
                    if (cropVideoListener != null) {
                        cropVideoListener.onScrollScreenLeftEdge(false);
                    }
                }

//                Log.d(TAG, "fixLayout.oriLeft: "+oriLeft);
//                if (oriRight - oriLeft >= maxWidth) {
//                    oriLeft = oriRight - maxWidth;
//                }

//                if (oriLeft <= 0) {
//                    oriLeft = 0;
//                }
//                if (oriRight - oriLeft - getDiffPadding() <= minWidth) {
//                    oriLeft = oriRight - minWidth - getDiffPadding();
//                }
                break;
            case RIGHT:
                oriRight = oriRight + scrollOffset;
                Log.d(TAG, "fixLayout.oriRight: " + oriRight);
                Log.d(TAG, "fixLayout.view width: " + getWidth());
                if (oriRight >= ScreenUtils.getAppScreenWidth()) {
                    oriRight = ScreenUtils.getAppScreenWidth();
                }
                //if (oriRight - oriLeft >= maxWidth) {
                //    oriRight = maxWidth + oriLeft;
                //}
                if (oriRight - oriLeft - getDiffPadding() <= minWidth) {
                    oriRight = oriLeft + minWidth + getDiffPadding();
                }
                break;
            default:
        }

        if (cropVideoListener != null) {
            cropVideoListener.onMove(dragAreaDirection, scrollOffset);
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        int newWidth = oriRight - oriLeft;
        Log.d(TAG, "fixLayout.newWidth: " + newWidth);
        params.width = newWidth;
        params.setMargins(oriLeft, getTop(), getRight(), getBottom());
        setLayoutParams(params);
    }

    public void justLeft(int scrollOffset){
        oriLeft = oriLeft + scrollOffset;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        int newWidth = oriRight - oriLeft;
        Log.d(TAG, "fixLayout.justLeft: " + newWidth);
        params.width = newWidth;
        params.setMargins(oriLeft, getTop(), getRight(), getBottom());
        setLayoutParams(params);
    }

    private int getDiffPadding() {
        return SizeUtils.dp2px(32) * 2;
    }

    private int getDirection(float x) {
        int left = getLeft();
        int right = getRight();

        if (x < touchAreaWidth) {
            return LEFT;
        }

        if (right - left - x < touchAreaWidth) {
            return RIGHT;
        }
        return CENTER;
    }

    private CropVideoListener cropVideoListener;

    public void setCropVideoListener(CropVideoListener cropVideoListener) {
        this.cropVideoListener = cropVideoListener;
    }

    public interface CropVideoListener {
        void onUp(int dragDirection);

        void onMove(int dragDirection, int moveValue);

        void onScrollScreenRightEdge();

        void onScrollScreenLeftEdge(boolean canScroll);
    }
}
