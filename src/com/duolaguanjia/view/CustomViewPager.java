package com.duolaguanjia.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
	private boolean enabled;// 滑动控制器

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.enabled = false;
	}

	public CustomViewPager(Context context) {
		super(context);
		this.enabled = false;
	}

	// 触摸没有反应
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onTouchEvent(event);
		}

		return false;
	}

	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onInterceptTouchEvent(event);
		}

		return false;
	}

	/**
	 * 设置当前ViewPager是否可滑动
	 */
	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 判断当前ViewPager 是否可滑动
	 */
	public boolean isEnabled() {
		return enabled;
	}

}
