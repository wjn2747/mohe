package com.duolaguanjia.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.app_sdk.tool.DensityUtil;
import com.app_sdk.tool.ScreenUtils;
import com.duolaguanjia.R;

import java.util.ArrayList;

public class IndicatorHorizontalScrollView extends HorizontalScrollView  implements View.OnClickListener
{
	private  String[] CHANELS;//标题
	private Context  context;
	private RadioGroup rg;
	public IndicatorHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
	}
	public IndicatorHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
	}
	/**
	 * 设置标题
	 * @param title  静态数组结构
	 */
	public  void setTitleArray(String[] title)
	{
		if (title==null||title.length==0) {
			throw new RuntimeException("标题不能为null");
		}
		CHANELS=title;
		init();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}

	/**
	 * 设置标题
	 * @param title  动态数组结构
	 */
	public  void setTitleArray(ArrayList<String> title)
	{
		if (title==null || title.size()==0) {
			throw new RuntimeException("标题不能为null");
			}
		//转换为数组
		CHANELS=(String[])title.toArray(new String[title.size()]);  
		init();
	}
	
  public RadioGroup	ferRadioGroup()
  {
	  return rg;
  }

	public IndicatorHorizontalScrollView(Context context) {
		super(context);
		this.context=context;
		init();

	}
	private void init()
	{
		if (CHANELS==null) {
			return ;
		}
		//初始化频道列表
		initChanels();
	}

	private void initChanels() {
		rg = new RadioGroup(context);
		rg.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		rg.setOrientation(RadioGroup.HORIZONTAL);
		rg.setGravity(Gravity.CENTER_VERTICAL);
		rg.setFadingEdgeLength(0);
		//按钮的宽度 等于屏幕的宽度/6
		int screenWidth= ScreenUtils.getScreenWH(context)[0];
		//判断数组的长度
		int len=0;//默认占屏幕的6分之一
		if (CHANELS.length<=4)
		{
			len=CHANELS.length;
		}else {
			len=4;
		}
		int rbWidth=screenWidth/len;
		//按照2个标题文字的情况下考虑
		for (int i = 0; i < CHANELS.length; i++) {
			View  item=	View.inflate(context,R.layout.view_order_indicator_item,null);
			TextView unread_address_number= (TextView) item.findViewById(R.id.unread_address_number);
			RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(DensityUtil.dip2px(context,17),DensityUtil.dip2px(context,17));
			layoutParams.setMargins(rbWidth/2+DensityUtil.dip2px(context,17),0,0,0);
			unread_address_number.setLayoutParams(layoutParams);
			RadioButton  rb= (RadioButton) item.findViewById(R.id.rg_item);
			rb.setOnClickListener(this);
			rb.setTag(i);
			rb.setLayoutParams(new RelativeLayout.LayoutParams(rbWidth, LayoutParams.FILL_PARENT));
			rb.setButtonDrawable(new BitmapDrawable());
			rb.setPadding(0, 0, 0, 0);
			rb.setText(CHANELS[i]);
			rb.setTextSize(17);
			rb.setTextColor(Color.WHITE);
			rb.setGravity(Gravity.CENTER);
			rg.addView(item);
		}

		addView(rg);
	}


	@Override
	public void onClick(View view)
	{
		if (onScrollClick!=null)
		{
			int  index=Integer.valueOf(view.getTag().toString());
			onScrollClick.onCheckedChanged(null,index);
		}

	}
	RadioGroup.OnCheckedChangeListener onScrollClick;
	public void setOnScrollClick(RadioGroup.OnCheckedChangeListener onScrollClick) {
		this.onScrollClick = onScrollClick;
	}
}
