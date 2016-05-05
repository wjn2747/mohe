package com.duolaguanjia.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app_sdk.tool.DensityUtil;
import com.app_sdk.tool.ScreenUtils;
import com.duolaguanjia.R;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class MainGridAdapter extends BaseAdapter {
	private Context mContext;

	public String[] img_text = { "订单", "朵拉小店","优惠" , "优惠券", "商家","我的设备"
			, "亲密付","用呗", "期待", };
	public int[] imgs = {  R.drawable.mohe_order,
			R.drawable.mohe_shop, R.drawable.mohe_youhui,R.drawable.mohe_youhuijuan,
			R.drawable.mohe_shangjia, R.drawable.mohe_shebei,
			R.drawable.love, R.drawable.card, 0 };

	public MainGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_item, parent, false);
			//设置view  的告诉
			int[]scroll=ScreenUtils.getScreenWH(mContext);
			convertView.findViewById(
					R.id.rl_border
			).setLayoutParams(new RelativeLayout.LayoutParams(scroll[0] / 3, (scroll[1] - (DensityUtil.dip2px(mContext, 50) + DensityUtil.dip2px(mContext, 57) + scroll[0] * 420 /720))/3));
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_image);
		if (imgs[position]>0)
		{
			iv.setBackgroundResource(imgs[position]);
		}else {
			iv.setVisibility(View.GONE);
		}
		if (img_text[position].equalsIgnoreCase(""))
		{
			tv.setVisibility(View.GONE);
		}else {
			tv.setVisibility(View.VISIBLE);
		}
		BaseViewHolder.get(convertView, R.id.tv_hint).setVisibility(View.GONE);
		//判断
		if (position==6 || position==7 || position==2 || position==3)
		{
			Log.e("position","position"+position);
	     	TextView tv_hint=	BaseViewHolder.get(convertView, R.id.tv_hint);
			tv_hint.setVisibility(View.VISIBLE);
		}
		tv.setText(img_text[position]);
		return convertView;
	}

}
