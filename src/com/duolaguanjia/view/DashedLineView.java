package com.duolaguanjia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;
import com.app_sdk.tool.ScreenUtils;

/**
 * 虚线下划线
 * 
 * @author
 * 
 */
public class DashedLineView extends View {

	public DashedLineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public DashedLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DashedLineView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 绘制一条虚线
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.rgb(37, 174, 182));
		Path path = new Path();
		path.moveTo(0, 0);
		path.lineTo(ScreenUtils.getScreenWH(getContext())[0], 0);
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
	}

}
