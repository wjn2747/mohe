package com.duolaguanjia.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.duolaguanjia.R;

public class WidgetMenuItem extends LinearLayout {
	private Context mContext;
	private TextView titleLbl;
	private ImageView icon, arrow;
	private  TextView tv_num,tv_account;




	public WidgetMenuItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initViews();
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MenuView);
		String text = a.getString(R.styleable.MenuView_menu_title);
		Drawable drawable = a.getDrawable(R.styleable.MenuView_menu_icon);
		Drawable drawableRight = a.getDrawable(R.styleable.MenuView_righticon);
		titleLbl.setTextSize(15);
		titleLbl.setTextColor(a.getColor(R.styleable.MenuView_textcolor,
				Color.RED));
		boolean checkable = a.getBoolean(R.styleable.MenuView_checkable, false);
		arrow.setVisibility(checkable ? GONE : VISIBLE);
		a.recycle();

		titleLbl.setText(text);
		if (drawableRight != null) {
			arrow.setImageDrawable(drawableRight);
		}
		if (drawable!=null)
		{
			icon.setVisibility(VISIBLE);
		}else {
			icon.setVisibility(GONE);
		}
		icon.setImageDrawable(drawable);
	}

	public void  setText(String txt)
	{
		titleLbl.setText(txt);
	}

	private void initViews() {
		View.inflate(mContext, R.layout.widget_menu_item, this);
		titleLbl = (TextView) findViewById(R.id.widget_menuitem_title);
		tv_account= (TextView) findViewById(R.id.tv_account);
		icon = (ImageView) findViewById(R.id.widget_menuitem_icon);
		arrow = (ImageView) findViewById(R.id.widget_menuitem_arrow);
		tv_num=(TextView) findViewById(R.id.tv_num);
	}
	public  String  getAccount()
	{
		return tv_account.getText().toString();

	}

	public  void  setAccount(String account)
	{
		if (TextUtils.isEmpty(account))
		{
			tv_account.setVisibility(GONE);
		}else {
			tv_account.setText(account);
			tv_account.setVisibility(VISIBLE);
		}

	}


	public  void  setNum(String  num)
	{
		tv_num.setText(num);
		tv_num.setVisibility(VISIBLE);
	}

	public void setArrowVisible(int visible) {
		arrow.setVisibility(visible);
	}

	public void setArrowImage(int res) {
		arrow.setImageResource(res);
	}

}
