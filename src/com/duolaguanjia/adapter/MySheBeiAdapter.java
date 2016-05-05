package com.duolaguanjia.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.duolaguanjia.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public class MySheBeiAdapter extends CommonBaseAdapter
{
    int  type;
    int[] imageList={R.drawable.icon_name,R.drawable.icon_time,R.drawable.icon_wath,R.drawable.icon_taiy,R.drawable.icon_live,R.drawable.icon_home};
    int[] imageList_no={R.drawable.icon_name_no,R.drawable.icon_time_no,R.drawable.icon_wath_no,R.drawable.icon_taiy_no,R.drawable.icon_live_no,R.drawable.icon_home_no};
    public MySheBeiAdapter(Context context, List mDatas, int itemLayoutId,int type) {
        super(context, mDatas, itemLayoutId);
        this.type=type;
    }

    @Override
    public void convert(CommonBaseViewHolder helper, Object item, int position) {
        helper.setText(R.id.tv_item,item.toString());
        ImageView iv = helper.getView(R.id.iv_image);
        iv.setImageResource(imageList[position]);
    }
}
