package com.duolaguanjia.activity.fragment.dingyue;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.view.xlistview.LaMaListView;
import com.duolaguanjia.R;
import com.google.gson.Gson;
import com.duolaguanjia.activity.DingYueSettingActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.DingYueBean;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.ClockModel;
import com.duolaguanjia.model.ServerMode;
import com.duolaguanjia.respone.ServerListRespone;
import com.duolaguanjia.view.pay.DialogWidget;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/6.
 */
public class AlreadDingYueFragment extends BaseFragment  implements View.OnClickListener{
    TitleManager titleManager;
    LaMaListView xlistviw;
    private CommonBaseAdapter commonBaseAdapter;
    private DialogWidget mDialogWidget;
    int  select=-1;//默认设置状态   1 取消订阅
    String xieyi = "<font color=" + "\"" + "#333333" + "\">" + "从下月起,"
            + "\"" + "您将不再订阅"  + "</font>" + "<u>"
            + "<font color=" + "\"" + "#576B95" + "\">" +  "\""+"闹钟服务"+ "\""
            + "</font>" + "</u>";
    String xiey2 = "<font color=" + "\"" + "#333333" + "\">" + "从下月起,"
            + "\"" + "您将不再订阅"  + "</font>" + "<u>"
            + "<font color=" + "\"" + "#576B95" + "\">" +  "\""+"睡前故事"+ "\""
            + "</font>" + "</u>";

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_xlistview  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("已订阅");
        titleManager.showRightTxt("编辑");
        titleManager.setRightOnClick(this);
        xlistviw= (LaMaListView) view.findViewById(R.id.xlistviw);
        xlistviw.setBackgroundColor(Color.WHITE);
        xlistviw.setPullLoadEnable(false);
        xlistviw.setDivider(new ColorDrawable(Color.parseColor("#DBDBDD")));
        xlistviw.setDividerHeight(2);
        xlistviw.setPullRefreshEnable(false);
        xlistviw.hideHeader();
        httpGetData();
        return view;
    }
    ArrayList<ServerMode> data;
    private  void httpGetData()
    {
        //已订阅
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"service/myService",baseActivity.params, ServerListRespone.class,new JsonObjectConve.HttpCallback<ServerListRespone>() {
            @Override
            public void onResponse(String s, ServerListRespone response, int code) {
                baseActivity. hideDialog();
                if (response!=null)
                {
                    if (response.getCode()==1)
                    {
                        data=response.getData();
                        initAdapter();
                    }
                }
            }
            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        });
    }
    private void  initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
            return;
        }
        commonBaseAdapter=new CommonBaseAdapter<ServerMode>(baseActivity,data,R.layout.adapter_alread_dingyue_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, ServerMode item, int position)
            {

                ImageView user_item=helper.getView(R.id.user_item);
                //设置图片
                if (item.getService_id().equalsIgnoreCase("2"))
                {
                    //睡前故事
                    user_item.setImageResource(R.drawable.sleep);
                    //隐藏
                    helper.getView(R.id.user_item_iv_avatar).setVisibility(View.GONE);
                    helper.getView(R.id.tv_name).setVisibility(View.GONE);
                }else {
                    //闹钟
                    user_item.setImageResource(R.drawable.clock);
                    helper.getView(R.id.user_item_iv_avatar).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_name).setVisibility(View.VISIBLE);
                    //设置小图片
                    baseActivity.imageLoader.displayImage(item.getClock_pic(), (ImageView) helper.getView(R.id.user_item_iv_avatar));
               //设置名称
                   helper.setText(R.id.tv_name,item.getClock_title()) ;
                }

                helper.setText(R.id.tv_time,item.getEnd_at());
                helper.setText(R.id.bt_play,item.getTitle());

                //设置名
                //
                if (select==-1)
                {
                   helper.setText(R.id.bt_ok,"设置");
                }else {
                    helper.setText(R.id.bt_ok,"取消订阅");
                }
                helper.getView(R.id.bt_ok).setTag(item);
                helper.getView(R.id.bt_ok).setOnClickListener(AlreadDingYueFragment.this);
            }
        };
        xlistviw.setAdapter(commonBaseAdapter);
    }

    /**
     * 取消订阅
     */
    private void  httpRemoveServer(final ServerMode serverMode)
    {
        baseActivity.params.clear();
        baseActivity.showDiaog();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.params.put("service_id",serverMode.getService_id());
        baseActivity.params.put("id",serverMode.getId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"service/cleService",baseActivity.params, ServerListRespone.class,new JsonObjectConve.HttpCallback<ServerListRespone>() {
            @Override
            public void onResponse(String s, ServerListRespone response, int code) {
                baseActivity. hideDialog();
                if (response!=null)
                {
                    if (response.getCode()==1)
                    {
                       data.remove(serverMode);
                        initAdapter();

                    }
                    baseActivity.DisPlay(response.getMsg());
                }
            }
            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        });

    }

    @Override
    public void onClick(View view) {
   switch (view.getId())
   {
       case R.id.bt_cancel:
           //评价取消
           mDialogWidget.dismiss();
           mDialogWidget=null;
           break;
       case R.id.tv_right_button:
           if (select<0)
           {
               select=1;
               titleManager.showRightTxt("取消");
           }else {
               select=-1;
               titleManager.showRightTxt("编辑");
           }
           commonBaseAdapter.notifyDataSetChanged();
           break;
       case R.id.bt_ok:
           ServerMode item= (ServerMode) view.getTag();
            if (select<0)
            {
                //设置
                //类别
                Bundle bundle=new Bundle();
                bundle.putString("id",item.getId());
                if (item.getService_id().equalsIgnoreCase("2"))
                {
                    bundle.putInt("type", DingYueBean.GUSHI);
                }else {
                    bundle.putInt("type", DingYueBean.NAOZHONG);
                }

                bundle.putString("title",item.getTitle());
                bundle.putString("clock_id",item.getId());
                ClockModel lingShengBean=new ClockModel();
                bundle.putString("data",new Gson().toJson(lingShengBean));
                baseActivity.openActivity(DingYueSettingActivity.class,bundle);
            }else {
                //取消订阅
                View pingjia_view=View.inflate(baseActivity,R.layout.dig_sure,null);
             TextView tv_content= (TextView) pingjia_view.findViewById(R.id.tv_content);
                if (item.getService_id().equalsIgnoreCase("2"))
                {
                    tv_content.setText(Html.fromHtml(xiey2));
                }else {
                    tv_content.setText(Html.fromHtml(xieyi));
                }
                //取消
                pingjia_view.findViewById(R.id.bt_cancel).setOnClickListener(this);
                pingjia_view.findViewById(R.id.bt_ok).setTag(item);
                pingjia_view.findViewById(R.id.bt_ok).setOnClickListener(onClickListener);
                mDialogWidget=new DialogWidget(baseActivity, pingjia_view);
                mDialogWidget.show();
          }
           break;
   }
    }
    private View.OnClickListener  onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ServerMode item= (ServerMode) view.getTag();
            httpRemoveServer(item);
            mDialogWidget.dismiss();
        }
    };
}
