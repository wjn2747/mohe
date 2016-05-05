package com.duolaguanjia.activity.fragment.preferential;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.tool.DensityUtil;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.tool.BitMapUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/27.
 */
public class PreferentialFragment extends BaseFragment {
    View   view;
    ListView listviw;
    private ArrayList<String> data=new ArrayList<String>();
    CommonBaseAdapter commonBaseAdapter;
    private int type_id;
    public static Fragment newInstance(int  id) {
        PreferentialFragment fragment = new PreferentialFragment();
        Bundle args = new Bundle();
        args.putInt("id",id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        type_id=getArguments().getInt("id");
        view =inflater.inflate(R.layout.view_listview, container, false);
        data.add("");
        data.add("");
        listviw= (ListView) view.findViewById(R.id.listviw);
        listviw.setBackgroundResource(R.color.whitesmoke);
        listviw.setDivider(new ColorDrawable(Color.parseColor("#F2F2F2")));
        listviw.setDividerHeight(DensityUtil.dip2px(baseActivity,10));
        listviw.setPadding(DensityUtil.dip2px(baseActivity,10),DensityUtil.dip2px(baseActivity,10),DensityUtil.dip2px(baseActivity,10),0);
        initAdapter();
        return view;
    }
    private  void initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
        }
        commonBaseAdapter=new CommonBaseAdapter<String>(baseActivity,data,R.layout.adapter_youhuijuan_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, String item, int position) {
                  if (type_id==1)
                  {
                      //图片灰度设置
                      ImageView user_item_iv_avatar=helper.getView(R.id.user_item_iv_avatar);
                      //设置图片
                      Bitmap bitmap= BitmapFactory.decodeResource(baseActivity.getResources(),R.drawable.bill_item_bg);
                      user_item_iv_avatar.setImageBitmap(BitMapUtil.grey(bitmap));
                  }
            }
        };
        listviw.setAdapter(commonBaseAdapter);
    }


}
