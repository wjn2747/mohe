package com.duolaguanjia.view;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import com.duolaguanjia.R;


/**
 * @author sunday 2013-12-04
 */
public class EditTextWithDel extends EditText {
	private final static String TAG = "EditTextWithDel";
	private Drawable imgInable;
	private Context mContext;

	public EditTextWithDel(Context context) {
		super(context);
		mContext = context;
		
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		imgInable = mContext.getResources().getDrawable(R.drawable.delete_gray);
		
//		setBackgroundResource(R.drawable.selecter_edittext);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			setDrawable();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				setDrawable();
			}

			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setDrawable();
	}

	// 设置删除图片
	private void setDrawable() {
		
		if (getText().toString().trim().length() < 1)
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		else
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
	}
	
	// 处理删除事件
	 /** 
     * 当手指抬起的位置在clean的图标的区域 
     * 我们将此视为进行清除操作 
     * getWidth():得到控件的宽度 
     * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的) 
     * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离 
     * getPaddingRight():clean的图标右边缘至控件右边缘的距离 
     * 于是: 
     * getWidth() - getTotalPaddingRight()表示: 
     * 控件左边到clean的图标左边缘的区域 
     * getWidth() - getPaddingRight()表示: 
     * 控件左边到clean的图标右边缘的区域 
     * 所以这两者之间的区域刚好是clean的图标的区域 
     */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if (imgInable != null && event.getAction() == MotionEvent.ACTION_UP) {
//			int eventX = (int) event.getRawX();
//			int eventY = (int) event.getRawY();
//			Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
//			Rect rect = new Rect();
//			getGlobalVisibleRect(rect);
//			rect.left = rect.right - 50;
//			if (rect.contains(eventX, eventY))
//				setText("");
//		}
		
		 switch (event.getAction()) { 
	        case MotionEvent.ACTION_UP: 
	            boolean isClean =(event.getX() > (getWidth() - getTotalPaddingRight()-20))&& 
	                             (event.getX() < (getWidth() - getPaddingRight())); 
	            if (isClean) { 
	                setText(""); 
	            } 
	            break; 
	        default: 
	            break; 
	        } 
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
