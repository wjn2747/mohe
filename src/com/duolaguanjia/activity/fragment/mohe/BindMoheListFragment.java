package com.duolaguanjia.activity.fragment.mohe;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BindMoheActivity;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.model.MoheBean;
import com.duolaguanjia.respone.MoHeRespone;
import com.duolaguanjia.view.swipemenulistview.*;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/27.
 */
public class BindMoheListFragment extends BaseFragment  implements AdapterView.OnItemClickListener ,View.OnClickListener
{
RelativeLayout rl_add;
    SwipeMenuListView listView;
    ArrayList<MoheBean> data=new ArrayList<>();
    AppAdapter   appAdapter;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mohe_list  , null, false);
        rl_add= (RelativeLayout) view.findViewById(R.id.rl_add);
        rl_add.setOnClickListener(this);
        listView= (SwipeMenuListView) view.findViewById(R.id.listView);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                       baseActivity);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(DensityUtil.dip2px(baseActivity,90));
                deleteItem.setIcon(
                        R.drawable.jiebang);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Bundle bundle=new Bundle();
                      //进入解绑页面
                bundle.putInt("branchType", BranhActivity.BRANCH_BAND_JIEBANG);
                bundle.putString("id", data.get(position).getMycode());
               baseActivity.openActivity(BranhActivity.class,bundle);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        httpGetDataMohe();
        super.onResume();
    }

    private  void  setAdapterList()
    {
        if (appAdapter!=null)
        {
            appAdapter.notifyDataSetChanged();
            return;
        }
        appAdapter = new AppAdapter();
        listView.setAdapter(appAdapter);
    }
    //获取魔盒列表
    private  void  httpGetDataMohe()
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/boxList",baseActivity.params, MoHeRespone.class,new JsonObjectConve.HttpCallback<MoHeRespone>() {
            @Override
            public void onResponse(String s, MoHeRespone response, int code) {
                baseActivity.hideDialog();
                data.clear();
                if (response!=null && response.getCode()==1)
                {
                    if (response.getCode()!=1)
                    {
                        baseActivity.DisPlay(response.getMsg());
                        return;
                    }
                    //得到魔盒
                    data.addAll(response.getData());
                    setAdapterList();
                }else {

                    baseActivity.DisPlay("获取魔盒列表失败1");
                }
            }

            @Override
            public void onError(String msg) {
               baseActivity. DisPlay(msg);
                baseActivity.hideDialog();
            }
        });



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onClick(View view) {
        if (view==rl_add)
        {
            //添加
            ((BindMoheActivity)baseActivity).next(3);
        }
    }

    class AppAdapter extends BaseSwipListAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public MoheBean getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(baseActivity,
                        R.layout.adapter_item_txt, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            MoheBean item = getItem(position);
            holder.tv_id.setText(item.getMycode());
            holder.tv_changjing.setText(item.getScene_title());
            return convertView;
        }

        class ViewHolder {
            TextView tv_id;
            TextView tv_changjing;

            public ViewHolder(View view) {
                tv_changjing = (TextView) view.findViewById(R.id.tv_changjing);
                tv_id = (TextView) view.findViewById(R.id.tv_id);
                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipEnableByPosition(int position) {
            if(position % 2 == 0){
                return false;
            }
            return true;
        }
    }
}
