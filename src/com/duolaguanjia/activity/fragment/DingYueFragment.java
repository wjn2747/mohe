package com.duolaguanjia.activity.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.view.AlertDialog;
import com.app_sdk.view.xlistview.LaMaListView;
import com.duolaguanjia.R;
import com.google.gson.Gson;
import com.duolaguanjia.activity.*;
import com.duolaguanjia.adapter.DingYueAdapter;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.DingYueBean;
import com.duolaguanjia.respone.ClicRespon;
import com.duolaguanjia.respone.DingYueRespone;
import com.duolaguanjia.respone.PayRespone;
import com.duolaguanjia.tool.PayDialog;
import com.duolaguanjia.view.pay.DialogWidget;
import com.duolaguanjia.view.pay.PayPasswordView;
import com.pingplusplus.android.PaymentActivity;

import java.util.ArrayList;

/**
 * Created by Night on 2015/11/2.
 */
public class DingYueFragment extends BaseFragment implements View.OnClickListener{
    View   view;
    public LaMaListView laMaListView;
    private DingYueAdapter dingYueAdapter;
    Button   tv_right_txt;
    private PayPasswordView.OnPayListener listener;
    PayDialog payDialog;
    private DialogWidget mDialogWidget;
    private ArrayList<DingYueBean> data=new ArrayList<DingYueBean>();
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_dingyue, container, false);
        tv_right_txt= (Button) view.findViewById(R.id.tv_right_button);
        tv_right_txt.setOnClickListener(this);
        laMaListView= (LaMaListView) view.findViewById(R.id.xlistviw);
       // data.add(new DingYueBean("朵拉天气",R.drawable.img_tianqi,"1","95%好评",DingYueBean.TIANQI));
        data.add(new DingYueBean("睡前故事",R.drawable.src_shuiqian,"2","95%好评",DingYueBean.GUSHI));
        data.add(new DingYueBean("闹铃",R.drawable.src_jiaoxing,"3","95%好评",DingYueBean.NAOZHONG));
//        data.add(new DingYueBean("朵拉资讯",R.drawable.src_news,"4","95%好评",DingYueBean.NEWS));
//        data.add(new DingYueBean("笑果",R.drawable.src_youmo,"5","95%好评",DingYueBean.XIAOGUO));
//        data.add(new DingYueBean("朵拉星座",R.drawable.src_xingzuo,"6","95%好评",DingYueBean.DUOLAXINGZUO));
//        data.add(new DingYueBean("有声小说",R.drawable.picture_book,"7","95%好评",DingYueBean.DUOLABOOK));
//        data.add(new DingYueBean("十万个为什么",R.drawable.picture_why,"8","95%好评",DingYueBean.SHIWANWHY));
//        data.add(new DingYueBean("朵拉英语",R.drawable.picture_english,"9","95%好评",DingYueBean.DUOLAENGLISH));
//        data.add(new DingYueBean("朵拉戏曲",R.drawable.picture_opera,"10人","95%好评",DingYueBean.DUOLAXIQU));
        setAdapterData();
        laMaListView.setBackgroundColor(Color.WHITE);
        laMaListView.setPullLoadEnable(false);
        laMaListView.setPullRefreshEnable(false);
        laMaListView.hideHeader();
        payDialog=new PayDialog(baseActivity);
        httpYanZhengDingYue("2");
        return view;
    }
    private  void  setAdapterData()
    {
        if (dingYueAdapter!=null)
        {
            dingYueAdapter.notifyDataSetChanged();
            return;
        }
        dingYueAdapter=new DingYueAdapter(data,baseActivity,onClickListener);
        laMaListView.setAdapter(dingYueAdapter);

    }
    int  pay_type=PayPasswordView.NORMAT;
    String  payTag="";
    String  patStr="";
    private View.OnClickListener patOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                    //提交支付请求
                    patStr=(String) view.getTag();
                    if (patStr.equalsIgnoreCase("ye"))
                    {
                        //判断是否输入了支付密码
                        if (TextUtils.isEmpty(baseActivity.application.getPayPws())  || baseActivity.application.getPayPws().equalsIgnoreCase("0"))
                        {
                            baseActivity.DisPlay("请先设置支付密码!");
                            //打开密码设置页面
                            Bundle bbb=new Bundle();
                            bbb.putInt("branchType",BranhActivity.BRANCH_USER_ACCOUNT_ANQUAN);
                            baseActivity.openActivity(BranhActivity.class,bbb);
                            return;
                        }
                        payTag=patStr;
                        mDialogWidget=new DialogWidget(baseActivity, getDecorViewDialog());
                        mDialogWidget.show();
                        payDialog.hideDialog();
                    }else {
                        //进入支付
                        payTag="";
                        httpPay("123");
                        payDialog.hideDialog();
                    }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == OrderActivity.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息\
                Log.e("errorMsg", "errorMsg" + errorMsg);
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             */
                if (result.equalsIgnoreCase("success")) {
                    baseActivity.DisPlay("付款成功!");
                    DingYueFragment.this.data.get(0).setDingyue(true);
                    dingYueAdapter.notifyDataSetChanged();
                } else if (result.equalsIgnoreCase("fail")) {
                    baseActivity.DisPlay("付款失败!");
                } else if (result.equalsIgnoreCase("cancel")) {
                    baseActivity.DisPlay("付款取消!");
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private  void httpPay(String password)
    {

        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("order_id",orderId);
        baseActivity.params.put("money",price);
        baseActivity.params.put("channel",patStr);
        baseActivity.params.put("rpass",password);
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"pay/payInit", baseActivity.params, PayRespone.class,new JsonObjectConve.HttpCallback<PayRespone>() {

            @Override
            public void onResponse(String jsonStr, PayRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    //判断COD  ID
                    if (response.getCode()!=1)
                    {
                        baseActivity.DisPlay(response.getMsg());

                        return;
                    }
                    if (payTag.equalsIgnoreCase("ye"))
                    {

                       baseActivity. DisPlay(response.getMsg());
                        httpYanZhengDingYue("2");
                        return;
                    }
                    Intent intent = new Intent();
                    String packageName = baseActivity.getPackageName();
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
                       baseActivity.startActivityForResult(intent, OrderActivity.REQUEST_CODE_PAYMENT);
                   }catch (Exception e)
                   {
                       e.printStackTrace();
                   }
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.hideDialog();
                baseActivity.DisPlay(msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_right_button:
                //订阅
                Bundle bundle=new Bundle();
                bundle.putInt("branchType",BranhActivity.BRANCH_ALREAD_DINGYUE);
                baseActivity.openActivity(BranhActivity.class,bundle);
                break;
        }

    }
    private  void  httpYanZhengDingYue(String  serverId)
    {
        baseActivity.params.clear();
        baseActivity.params.put("service_id",serverId);
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"service/digService",baseActivity.params, DingYueRespone.class,new JsonObjectConve.HttpCallback<DingYueRespone>() {
            @Override
            public void onResponse(String s, DingYueRespone response, int code) {
               baseActivity. hideDialog();
                if (response!=null)
                {
                   if (response.getCode()==1)
                   {
                       if (response.getData().getIsDy()==1)
                       {
                           //故事已经被订阅
                           data.get(0).setDingyue(true);
                           dingYueAdapter.notifyDataSetChanged();
                       }
                       data.get(0).setPrice(response.getData().getPrice());
                   }
                }
            }
            @Override
            public void onError(String msg) {
     baseActivity.DisPlay(msg);
            }
        });
    }
    String price="";
    String proName="";
    public String  orderId;
    private   void  httpDingyueServer(final String prices)
    {
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"service/readStory",baseActivity.params, ClicRespon.class,new JsonObjectConve.HttpCallback<ClicRespon>() {
            @Override
            public void onResponse(String s, ClicRespon response, int code) {
                baseActivity. hideDialog();
                if (response!=null)
                {
                    if (response.getCode()==1)
                    {
                        //数据获取成功;
                        if (!TextUtils.isEmpty(response.getData())) {
                            orderId=response.getData();
                            DingYueFragment.this.price =prices;
                            proName="睡前故事";
                            //订单需要支付
                            //弹出支付类型
                            if (TextUtils.isEmpty(payTag))
                            {
                                //选择支付类型
                                payDialog.orderId=response.getData();
                                payDialog.showPayView(patOnClick);
                            }else {
                                mDialogWidget=new DialogWidget(baseActivity, getDecorViewDialog());
                                mDialogWidget.show();
                            }

                        }else {
                            //不需要支付直接返回
                            //故事已经被订阅
                            data.get(0).setDingyue(true);
                            dingYueAdapter.notifyDataSetChanged();
                        }

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


    private View.OnClickListener  onClickListener=new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         //确认收货
         if (!baseActivity.application.isBdMohe())
         {
             new AlertDialog(baseActivity).builder().setTitle("设置魔盒")
                     .setPositiveButton("确认", new View.OnClickListener() {
                         @Override
                         public void onClick(View v)
                         {
                             baseActivity.openActivity(BindMoheActivity.class);
                         }
                     }).show();
             return;
         }
         DingYueBean dingYueBean= (DingYueBean) view.getTag();
         switch (dingYueBean.getType())
         {
             case DingYueBean.GUSHI:
                 //儿童故事
                 //弹出对话框
                 //确认收货
                 if (dingYueBean.isDingyue())
                 {
                     baseActivity.DisPlay("该服务已被订阅!");
                     return;
                 }
                    //判断是否免费
                     //免费
                     httpDingyueServer(dingYueBean.getPrice());
                 break;
             case DingYueBean.NAOZHONG:
                 //闹钟
                 Intent intent=new Intent(baseActivity, ListViewSelectActivity.class);
                 intent.putExtra("title","铃声选择");
                 intent.putExtra("type",DingYueBean.NAOZHONG);
                 intent.putExtra("data",new Gson().toJson(dingYueBean));
                 baseActivity.startActivityForResult(intent,5);
                 break;
         }
     }
 };
    protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance(proName,payTag,price,baseActivity,new PayPasswordView.OnPayListener() {

            @Override
            public void onSurePay(String password) {

                mDialogWidget.dismiss();
                mDialogWidget=null;
                switch (pay_type)
                {
                    case PayPasswordView.NORMAT:
                        //付款
                        httpPay(password);
                        break;
                    case PayPasswordView.PWS_SURE:
                        //确认收货
                        baseActivity.DisPlay("确认收货");
                        break;
                }
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
                baseActivity.DisPlay("付款已取消");
            }
        },pay_type).getView();
    }

}
