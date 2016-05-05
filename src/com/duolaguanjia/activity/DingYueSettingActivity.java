package com.duolaguanjia.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.R;
import com.google.gson.Gson;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.bean.DingYueBean;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.ClockListModel;
import com.duolaguanjia.model.ClockModel;
import com.duolaguanjia.respone.ClicRespon;
import com.duolaguanjia.respone.ClolcRespone;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/6.
 */
public class DingYueSettingActivity extends BaseActivity  implements View.OnClickListener{
    RelativeLayout rl_add,ll_open_colse;
    int type;//类型
    String id;
    String title;
    Button bt_open;
    private  String  clock_id;
    ImageView user_item_iv_avatar;
    TextView tv_txt;
    ListView listviw;
    TitleManager titleManager;
    private CommonBaseAdapter commonBaseAdapter;
    private void  initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
            return;
        }
        commonBaseAdapter=new CommonBaseAdapter<ClockListModel>(this,data,R.layout.adapter_time_settin_view) {
            @Override
            public void convert(CommonBaseViewHolder helper, ClockListModel item, int position)
            {
                helper.setText(R.id.tv_time,item.getHour()+":"+item.getMinutes());
                //设置周
                helper.setText(R.id.tv_day,item.getWeek());
                helper.getView(R.id.ib_open).setTag(item.getId());
                if (item.getIs_open().equalsIgnoreCase("1"))
                {
                    helper.getView(R.id.ib_open).setSelected(true);
                }else {
                    //没开启
                    helper.getView(R.id.ib_open).setSelected(false);
                }
                helper.getView(R.id.ib_open).setOnClickListener(DingYueSettingActivity.this);
            }
        };
        listviw.setAdapter(commonBaseAdapter);
    }

    private  void gushiOpen()
    {
        params.clear();
        params.put("cust_id",application.getUserId());
        jsonObjectConve.httpPost(AppApplication.HOST+"service/data",params, ClicRespon.class,new JsonObjectConve.HttpCallback<ClicRespon>() {
            @Override
            public void onResponse(String s, ClicRespon response, int code) {
                hideDialog();
                if (response!=null)
                {
                    if (response.getData()!=null)
                    {
                        if (response.getData().equalsIgnoreCase("1"))
                        {
                            bt_open.setSelected(!bt_open.isSelected());
                        }

                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialog();
            }
        });


    }
    private  void setGuShiOpen()
    {
        params.clear();
        showDiaog();
        params.put("cust_id",application.getUserId());
        jsonObjectConve.httpPost(AppApplication.HOST+"service/openStory",params, ClolcRespone.class,new JsonObjectConve.HttpCallback<ClolcRespone>() {
            @Override
            public void onResponse(String s, ClolcRespone response, int code) {
                hideDialog();
                if (response!=null)
                {
                    DisPlay(response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                DisPlay(msg);
                hideDialog();
            }
        });


    }

    @Override
    protected void onResume() {
  if (type==DingYueBean.NAOZHONG)
  {
      httpGetData();
  }
        super.onResume();
    }
    ClockModel lingShengBean;
    @Override
    public void initView(Bundle savedInstanceState) {
        id=getIntent().getStringExtra("id");
        type=getIntent().getIntExtra("type",0);
        title=getIntent().getStringExtra("title");
        clock_id=getIntent().getStringExtra("clock_id");
        if (getIntent().getStringExtra("data")!=null)
        {
            //转换
            lingShengBean=new Gson().fromJson(getIntent().getStringExtra("data"),ClockModel.class);
        }
        setContentView(R.layout.fragment_dingyue_ok);
        titleManager=new TitleManager(findViewById(R.id.ll_title),this);
        titleManager.setTitleName("设置");
        rl_add= (RelativeLayout) findViewById(R.id.rl_add);
        bt_open= (Button) findViewById(R.id.bt_open);
        user_item_iv_avatar= (ImageView) findViewById(R.id.user_item_iv_avatar);
        bt_open.setOnClickListener(this);
        ll_open_colse= (RelativeLayout) findViewById(R.id.ll_open_colse);
        ll_open_colse.setOnClickListener(this);
        listviw= (ListView) findViewById(R.id.listviw);
        tv_txt= (TextView) findViewById(R.id.tv_txt);
        rl_add.setOnClickListener(this);
        if (type== DingYueBean.GUSHI)
        {
            listviw.setVisibility(View.GONE);
            ll_open_colse.setVisibility(View.VISIBLE);
            gushiOpen();
            //修改名称
            tv_txt.setText("睡前故事");
        }
        if (TextUtils.isEmpty(clock_id) || clock_id.equalsIgnoreCase("0"))
        {
            rl_add.setVisibility(View.GONE);
            //睡前故事
            user_item_iv_avatar.setImageResource(R.drawable.sleep);
        }else {
            //闹钟
            user_item_iv_avatar.setImageResource(R.drawable.clock);
        }
    }
    ArrayList<ClockListModel>  data=new ArrayList<>();
    private  void httpGetData()
    {
        params.clear();
        showDiaog();
        params.put("cust_id",application.getUserId());
        params.put("clock_id",clock_id);
        jsonObjectConve.httpPost(AppApplication.HOST+"service/clockList",params, ClolcRespone.class,new JsonObjectConve.HttpCallback<ClolcRespone>() {
            @Override
            public void onResponse(String s, ClolcRespone response, int code) {
                hideDialog();
                if (response!=null)
                {
                    if (response.getData()!=null)
                    {
                        data.clear();
                        data.addAll(response.getData());
                        initAdapter();
                    }
                }
            }

            @Override
            public void onError(String msg) {
                DisPlay(msg);
                hideDialog();
            }
        });
    }

    @Override
    public void httpError(String code, String response) {

    }

    /**
     * 开关
     */
    private   void  httpOpenClose(String  id)
    {
        showDiaog();
        params.clear();
        params.put("cust_id",application.getUserId());
        params.put("id",id);
        jsonObjectConve.httpPost(AppApplication.HOST+"service/openClock",params, ClolcRespone.class,new JsonObjectConve.HttpCallback<ClolcRespone>() {
            @Override
            public void onResponse(String s, ClolcRespone response, int code) {
                hideDialog();
                if (response!=null)
                {
                    DisPlay(response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                DisPlay(msg);
                hideDialog();
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (view==bt_open)
        {
            //open
            bt_open.setSelected(!bt_open.isSelected());
            //闹钟开关
            setGuShiOpen();
        }

         if (view==rl_add)
         {
             Bundle bundle=new Bundle();
             bundle.putInt("branchType", BranhActivity.BRANCH_DINGYUE_SETTING);
             bundle.putString("title",title);
             bundle.putInt("type",type);
             bundle.putString("clock_id",clock_id);
             bundle.putString("data",new Gson().toJson(lingShengBean));
             openActivity(BranhActivity.class,bundle);
         }
        if (view.getId()==R.id.ib_open)
        {
            view.setSelected(!view.isSelected());
            httpOpenClose((String) view.getTag());
        }
    }
}
