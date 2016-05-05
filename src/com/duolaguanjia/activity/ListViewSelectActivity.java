package com.duolaguanjia.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.app_sdk.view.xlistview.LaMaListView;
import com.duolaguanjia.R;
import com.google.gson.Gson;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.bean.DingYueBean;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.ClockModel;
import com.duolaguanjia.respone.ClicRespon;
import com.duolaguanjia.respone.PayRespone;
import com.duolaguanjia.respone.ServerClockRespone;
import com.duolaguanjia.tool.MediaManager;
import com.duolaguanjia.tool.PayDialog;
import com.duolaguanjia.view.pay.DialogWidget;
import com.duolaguanjia.view.pay.PayPasswordView;
import com.pingplusplus.android.PaymentActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/6.
 */
public class ListViewSelectActivity  extends BaseActivity implements View.OnClickListener{
    private  int type;
    String title;
    private ArrayList<ClockModel> data;
    LaMaListView laMaListView;
    TitleManager titleManager;
    private CommonBaseAdapter commonBaseAdapter;
    private DialogWidget mDialogWidget;
    DingYueBean dingYueBean;
    PayDialog payDialog;

    /**
     * 获取闹钟性情
     */
    private   void  httpServerDescript(int  id)
    {
        showDiaog();
        params.clear();
        params.put("cust_id",application.getUserId());
        jsonObjectConve.httpPost(AppApplication.HOST+"service/clockService",params, ServerClockRespone.class,new JsonObjectConve.HttpCallback<ServerClockRespone>() {
            @Override
            public void onResponse(String s, ServerClockRespone response, int code) {
                if (response!=null)
                {
                    if (response.getCode()==1 && response.getData()!=null)
                    {
                        //数据获取成功;
                        data=response.getData();
                    }
                    initAdapter();
                }
                hideDialog();
            }
            @Override
            public void onError(String msg) {
                hideDialog();
                 DisPlay(msg);
            }
        });

    }
    @Override
    public void initView(Bundle savedInstanceState) {
        title=getIntent().getStringExtra("title");
        type=getIntent().getIntExtra("type",0);
        dingYueBean=new Gson().fromJson(getIntent().getStringExtra("data"),DingYueBean.class);
        httpServerDescript(type);
        setContentView(R.layout.view_xlistview);
        laMaListView= (LaMaListView) findViewById(R.id.xlistviw);
        laMaListView.setBackgroundColor(Color.WHITE);
        laMaListView.setPullLoadEnable(false);
        laMaListView.setDivider(new ColorDrawable(Color.parseColor("#DBDBDD")));
        laMaListView.setDividerHeight(DensityUtil.dip2px(this,1));
        laMaListView.setPullRefreshEnable(false);
        laMaListView.hideHeader();
        payDialog=new PayDialog(this);
        titleManager=new TitleManager(findViewById(R.id.ll_title),this);
        titleManager.setTitleName(title);
    }
    View firstView;
    private  View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final ClockModel item= (ClockModel) view.getTag();
            if (item.isPlay())
            {
                //正在播放 停止
                MediaManager.pause();
                item.setPlay(false);
                return;
            }
            if (firstView!=null &&  firstView==view && !item.isPlay())
            {
                MediaManager.onResume();
                item.setPlay(true);
                return;
            }
            firstView=view;
            //判断文件是否存在
             if (item.getClock_url().startsWith("http://")){
                 item.setPlay(true);
                 DisPlay("声音准备中...");
                 //网络音乐
                 MediaManager.playSound(item.getClock_url(), new MediaPlayer.OnCompletionListener() {
                     @Override
                     public void onCompletion(MediaPlayer mediaPlayer) {
                          DisPlay("播放结束");
                         item.setPlay(false);
                     }
                 });

             }else {
                 DisPlay("请检查文件的路径");

             }
        }
    };

    @Override
    protected void onDestroy() {
        MediaManager.release();
        super.onDestroy();
    }

    private  void  initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
            return;
        }
        commonBaseAdapter=new CommonBaseAdapter<ClockModel>(this,data,R.layout.adapter_lingsheng_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, ClockModel item, int position)
            {
                if (type== DingYueBean.NAOZHONG)
                {
                    //设置图片
                    imageLoader.displayImage(item.getClock_pic(), (ImageView) helper.getView(R.id.user_item_iv_avatar));
                    //铃声
                    helper.setText(R.id.bt_play,item.getClock_title());
                    helper.getView(R.id.bt_play).setTag(item);
                    helper.getView(R.id.bt_play).setOnClickListener(onClickListener);
                    helper.getView(R.id.bt_ok).setTag(item);
                    helper.getView(R.id.bt_ok).setOnClickListener(ListViewSelectActivity.this);
                    //免费 收费
                    if (Float.valueOf(item.getPrice())>0)
                    {
                        helper.setText(R.id.bt_ok,item.getPrice()+"元/月");
                    }else {
                        helper.setText(R.id.bt_ok,"免费");

                    }
                    //设置是否订阅
                    if (item.getIs_on()==1)
                    {
                        //收藏
                        helper.setText(R.id.bt_ok,"已订阅");
                    }
                }
            }
        };
        laMaListView.setAdapter(commonBaseAdapter);
    }
    int  pay_type=PayPasswordView.NORMAT;
    protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance(proName,payTag,price,this,new PayPasswordView.OnPayListener() {

            @Override
            public void onSurePay(String password) {
                // TODO Auto-generated method stub
                mDialogWidget.dismiss();
                mDialogWidget=null;
                httpPay(password);
            }

            @Override
            public void onMore() {
                //更多支付
                mDialogWidget.dismiss();
                mDialogWidget=null;
                payDialog.showPayView(patOnClick);
            }

            @Override
            public void onCancelPay() {
                // TODO Auto-generated method stub
                mDialogWidget.dismiss();
                mDialogWidget=null;
                DisPlay("付款已取消");
            }
        },pay_type).getView();
    }

    private View.OnClickListener patOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
                case R.id.ll_add_new:
                    //添加新卡
                    Bundle bundle=new Bundle();
                    bundle.putInt("branchType", BranhActivity.BRANCH_ADD_BANK);
                    openActivity(BranhActivity.class,bundle);
                    break;
                default:
                    //提交支付请求
                    patStr=(String) view.getTag();
                    if (patStr.equalsIgnoreCase("ye"))
                    {
                        //判断是否输入了支付密码
                        if (TextUtils.isEmpty(application.getPayPws())  || application.getPayPws().equalsIgnoreCase("0"))
                        {
                            DisPlay("请先设置支付密码!");
                            //打开密码设置页面
                            Bundle bbb=new Bundle();
                            bbb.putInt("branchType",BranhActivity.BRANCH_USER_ACCOUNT_ANQUAN);
                            openActivity(BranhActivity.class,bbb);
                            return;
                        }
                        payTag=patStr;
                        mDialogWidget=new DialogWidget(ListViewSelectActivity.this, getDecorViewDialog());
                        mDialogWidget.show();
                        payDialog.hideDialog();
                    }else {
                        //进入支付
                        payTag="";
                        httpPay("1");
                        payDialog.hideDialog();
                    }
                    break;
            }
        }
    };


    private  void httpPay(String  password)
    {
        if (TextUtils.isEmpty(payDialog.orderId))
        {
            DisPlay("无法获取到订单号!");
            return;
        }

        showDiaog();
        params.clear();
       params.put("order_id",payDialog.orderId);
        params.put("money",price);
       params.put("channel",patStr);
      params.put("rpass",password);
       params.put("cust_id",application.getUserId());
      jsonObjectConve.httpPost(AppApplication.HOST+"pay/payInit",params, PayRespone.class,new JsonObjectConve.HttpCallback<PayRespone>() {

            @Override
            public void onResponse(String jsonStr, PayRespone response, int code) {
               hideDialog();
                if (response!=null)
                {
                    //判断COD  ID
                    if (response.getCode()!=1)
                    {
                       DisPlay(response.getMsg());

                        return;
                    }
                    if (payTag.equalsIgnoreCase("ye"))
                    {
                        DisPlay(response.getMsg());
                        onResult();
                        return;
                    }

                    Intent intent = new Intent();
                    String packageName =getPackageName();
                    ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                    intent.setComponent(componentName);
                    try {
                        String  str=response.getTxt();
                        str=str.substring(str.indexOf(":")+1, str.length());
                        str=str.substring(0,str.indexOf("\"code")).trim();
                        str=str.substring(0,str.length()-1).toString();
                        Log.e("str",str);
                        //  intent.putExtra(PaymentActivity.EXTRA_CHARGE, new Gson().toJson(response.getData()));
                        intent.putExtra(PaymentActivity.EXTRA_CHARGE,str.trim());
                        //    intent.putExtra(PaymentActivity.EXTRA_CHARGE, new Gson().toJson(response.getData()));
                        startActivityForResult(intent, OrderActivity.REQUEST_CODE_PAYMENT);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialog();
              DisPlay(msg);
            }
        });
    }
    @Override
    public void httpError(String code, String response) {

    }
    String  payTag="";
    String  patStr="";
    String price="";
    String proName="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        Log.e("onActivityResult","onActivityResult"+requestCode+"sss"+"resultCode"+resultCode);
        if (requestCode == OrderActivity.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息\
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             */
                Log.e("errorMsg","errorMsg"+errorMsg);
                if (result.equalsIgnoreCase("success"))
                {
                   onResult();
                }else  if (result.equalsIgnoreCase("fail"))
                {
                    DisPlay("付款失败!");
                }else if (result.equalsIgnoreCase("cancel"))
                {
                    DisPlay("付款取消!");
                }


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    ClockModel lingShengBean;
    private  void  onResult()
    {
        if (lingShengBean==null)
        {
            DisPlay("没有数据");
            return;
        }
               Bundle bundle=new Bundle();
                 bundle.putInt("branchType", BranhActivity.BRANCH_DINGYUE_SETTING);
                 bundle.putString("title",dingYueBean.getName());
                 bundle.putInt("type",dingYueBean.getType());
        bundle.putString("data",new Gson().toJson(lingShengBean));
                 openActivity(BranhActivity.class,bundle);
        finish();
    }

    private  void  dingyueNaoZhong(String  id, final ClockModel lingShengBean)
    {
        params.clear();
        showDiaog();
        params.put("clock_id",id);
        params.put("cust_id",application.getUserId());
        jsonObjectConve.httpPost(AppApplication.HOST+"service/digClocks",params, ClicRespon.class,new JsonObjectConve.HttpCallback<ClicRespon>() {
            @Override
            public void onResponse(String s, ClicRespon response, int code) {
                hideDialog();
                if (response!=null)
                {
                    if (response.getCode()==1 )
                    {
                        //数据获取成功;
                        if (!TextUtils.isEmpty(response.getData())) {
                            price=lingShengBean.getPrice();
                            proName=lingShengBean.getClock_title();
                            //订单需要支付
                            //弹出支付类型
                            if (TextUtils.isEmpty(payTag))
                            {
                                //选择支付类型
                                payDialog.orderId=response.getData();
                              payDialog.showPayView(patOnClick);
                            }else {
                                mDialogWidget=new DialogWidget(ListViewSelectActivity.this, getDecorViewDialog());
                                mDialogWidget.show();
                            }

                        }else {
                            //不需要支付直接返回
                            //直接返回
                            ListViewSelectActivity.this.lingShengBean=lingShengBean;
                            onResult();
                        }

                    }
                    DisPlay(response.getMsg());
                }
            }
            @Override
            public void onError(String msg) {
                hideDialog();
                DisPlay(msg);
            }
        });



    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_ok:
                //订阅

               if (type== DingYueBean.NAOZHONG)
              {
                   //闹钟
                  ClockModel lingShengBean= (ClockModel) view.getTag();
                  //服务器查询订阅
                  dingyueNaoZhong(lingShengBean.getClock_id(),lingShengBean);
               }
                break;
        }

    }
}
