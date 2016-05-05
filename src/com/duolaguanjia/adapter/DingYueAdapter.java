package com.duolaguanjia.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.duolaguanjia.R;
import com.duolaguanjia.bean.DingYueBean;

import java.util.ArrayList;

/**
 * Created by Night on 2015/11/2.
 */
public class DingYueAdapter extends BaseAdapter {
    private ArrayList<DingYueBean> data;
    private Context mContext;
    View.OnClickListener onClickListener;
    View view;
    public  DingYueAdapter(ArrayList<DingYueBean> data, Context context, View.OnClickListener onClickListener)
    {
        this.data=data;
        this.onClickListener=onClickListener;
        this.mContext=context;

    }
    @Override
    public int getCount() {
        if (data == null || data.size() == 0)
            return 0;
            return data.size() / 2 + data.size() % 2;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_dingyue_item,null);
            holder = new ViewHolder();
            view=convertView;
            holder.iv_left_avatar= (ImageView) convertView.findViewById(R.id.iv_left_avatar);
            holder.iv_right_avatar= (ImageView) convertView.findViewById(R.id.iv_right_avatar);
            holder.tv_left_title= (TextView) convertView.findViewById(R.id.tv_left_title);
            holder.tv_right_title= (TextView) convertView.findViewById(R.id.tv_right_title);
            holder.tv_left_dingyue= (TextView) convertView.findViewById(R.id.tv_left_dingyue);
            holder.tv_left_descript= (TextView) convertView.findViewById(R.id.tv_left_descript);
            holder.tv_right_dingyue= (TextView) convertView.findViewById(R.id.tv_right_dingyue);
            holder.tv_right_descript= (TextView) convertView.findViewById(R.id.tv_right_descript);
            holder.tv_left_ding= (TextView) convertView.findViewById(R.id.tv_left_ding);
            holder.tv_right_ding= (TextView) convertView.findViewById(R.id.tv_right_ding);
            holder.ll_left_item= (LinearLayout) convertView.findViewById(R.id.ll_left_item);
            holder.ll_right_item= (LinearLayout) convertView.findViewById(R.id.ll_right_item);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        DingYueBean  leftBean= data.get(i*2);
        DingYueBean  rightBean= data.get(i*2+1);
        if (leftBean.isDingyue())
        {
            holder.tv_left_ding.setText("已订阅");
        }
        holder.ll_left_item.setTag(leftBean);
        holder.ll_right_item.setTag(rightBean);
        holder.ll_left_item.setOnClickListener(onClickListener);
        holder.ll_right_item.setOnClickListener(onClickListener);
        holder.iv_left_avatar.setBackgroundResource(leftBean.getSrc());//左边图片
        holder.iv_right_avatar.setBackgroundResource(rightBean.getSrc());//右边图片
        //名字
        holder.tv_left_title.setText(leftBean.getName());
        holder.tv_right_title.setText(rightBean.getName());

        return convertView;
    }


    public  static class ViewHolder {
        LinearLayout ll_left_item,ll_right_item;
        ImageView iv_left_avatar,iv_right_avatar;
        TextView tv_left_ding, tv_right_ding,tv_left_title,tv_right_title,tv_left_dingyue,tv_left_descript,tv_right_dingyue,tv_right_descript;
    }
}
