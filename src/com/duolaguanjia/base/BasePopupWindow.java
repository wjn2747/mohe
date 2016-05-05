package com.duolaguanjia.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Night on 2015/8/10.
 */
public abstract class BasePopupWindow extends PopupWindow
{
    protected View mContentView;
    protected onSubmitClickListener mOnSubmitClickListener;

    public BasePopupWindow() {
        super();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BasePopupWindow(Context context, AttributeSet attrs,
                           int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePopupWindow(Context context) {
        super(context);
    }

    public BasePopupWindow(int width, int height) {
        super(width, height);
    }

    public BasePopupWindow(View contentView, int width, int height,
                           boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public BasePopupWindow(View contentView) {
        super(contentView);
    }
    public  Context context;
    @SuppressWarnings("deprecation")
    public BasePopupWindow(View contentView, int width, int height,Context context) {
        super(contentView, width, height, true);
        this.context=context;
        mContentView = contentView;
        setBackgroundDrawable(new BitmapDrawable());
        initViews();
        initEvents();
        init();
    }

    public abstract void initViews();

    public abstract void initEvents();

    public abstract void init();

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }

    /**
     * 显示在parent的上部并水平居中
     *
     * @param parent
     */
    public void showViewTopCenter(View parent) {
        showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 显示在parent的中心
     *
     * @param parent
     */
    public void showViewCenter(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    /**
     * 添加确认单击监听
     *
     * @param l
     */
    public void setOnSubmitClickListener(onSubmitClickListener l) {
        mOnSubmitClickListener = l;
    }

    public interface onSubmitClickListener {
        void onClick();
    }

}
