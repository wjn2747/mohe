package com.duolaguanjia.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.duolaguanjia.R;
import com.duolaguanjia.bean.IconBean;
import com.duolaguanjia.tool.WidgetUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/2.
 */
public class ShangJiaDesciptIconSelectView extends LinearLayout {
    GridView sp_scrol;
    ListView listviw;
    ArrayList<IconBean> icon_data,item_data;
    Context context;
    private CommonBaseAdapter icon_commonBaseAdapter, listView_commonBaseAdapter;
    public ShangJiaDesciptIconSelectView(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    public ShangJiaDesciptIconSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(context);
    }

    private  void initListViewAdapter()
    {
        //设置listview 的高度
        listView_commonBaseAdapter=new CommonBaseAdapter<IconBean>(context,item_data, R.layout.adapter_eat_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, IconBean item, int position) {

            }
        };
        listviw.setAdapter(listView_commonBaseAdapter);
        WidgetUtil.setListViewHeightBasedOnChildren(listviw);

    }
    private  void  initGridViewAdapter()
    {

        icon_commonBaseAdapter=new CommonBaseAdapter<IconBean>(context,icon_data,R.layout.adapter_shebei_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, IconBean item, int position) {
                helper.setText(R.id.tv_item,item.getTitleName());
                ImageView iv = helper.getView(R.id.iv_image);
                iv.setImageResource(item.getIcon());
            }
        };
        sp_scrol.setAdapter(icon_commonBaseAdapter);
        sp_scrol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                item_data.clear();
                for (int j = 0; j <i ; j++) {
                    item_data.add(new IconBean("新建",R.drawable.smallpizza));
                }
                Log.e("icon_data","icon_data"+item_data.size());
                initListViewAdapter();
            }
        });

    }
    private void init(Context context)
    {
        View.inflate(context, R.layout.view_icon_select, this);
        sp_scrol= (GridView) findViewById(R.id.sp_scroll);
        listviw= (ListView) findViewById(R.id.listviw);
        icon_data=new ArrayList<IconBean>();
        item_data=new ArrayList<IconBean>();
        item_data.add(new IconBean("1",R.drawable.smallpizza));
        item_data.add(new IconBean("2",R.drawable.smallpizza));
        icon_data.add(new IconBean("1",R.drawable.smallpizza));
        icon_data.add(new IconBean("2",R.drawable.smallpizza));
        icon_data.add(new IconBean("3",R.drawable.smallpizza));
        icon_data.add(new IconBean("4",R.drawable.smallpizza));
        icon_data.add(new IconBean("5",R.drawable.smallpizza));
        icon_data.add(new IconBean("6",R.drawable.smallpizza));
        icon_data.add(new IconBean("7",R.drawable.smallpizza));
        icon_data.add(new IconBean("8",R.drawable.smallpizza));
        initGridViewAdapter();
        initListViewAdapter();
    }
}
