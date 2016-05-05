package com.duolaguanjia.activity.fragment.order;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.app_sdk.tool.ToastUtil;
import com.app_sdk.view.AlertDialog;
import com.app_sdk.view.xlistview.LaMaListView;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.activity.OrderActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.CodeRespone;
import com.duolaguanjia.bean.LoginRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.OrderModel;
import com.duolaguanjia.model.ProModel;
import com.duolaguanjia.respone.OrderRespaone;
import com.duolaguanjia.respone.PayRespone;
import com.duolaguanjia.tool.PayDialog;
import com.duolaguanjia.tool.Utility;
import com.duolaguanjia.view.EditTextWithDel;
import com.duolaguanjia.view.pay.DialogWidget;
import com.duolaguanjia.view.pay.PayPasswordView;
import com.pingplusplus.android.PaymentActivity;

import java.util.*;

/**
 * Created by Administrator on 2015/12/25.
 */
public class ListViewOrderFragment extends BaseFragment  implements View.OnClickListener
{
    LaMaListView listviw;
    PayDialog payDialog;
    TitleManager titleManager;
    TextView tv_nodata;
      public int   loadType=0;//5 刷新  6  家在
    private CommonBaseAdapter<OrderModel> commonBaseAdapter;
    public final static int ORDER_ALL=1;//全部订单
    public final static int ORDER_WAIT_PAY=2;//待支付
    public final static int ORDER_WAIT_DEND=3;//待发货
    public final static int ORDER_WAIT_SHOUHUO=4;//待收货
    public  final static int ORDER_WAIT_PINGJIA=5;//待评价
    public   final static int ORDER_WAIT_BACK=6;//售后
    private PayPasswordView.OnPayListener listener;
    private DialogWidget mDialogWidget;
    ArrayList<OrderModel> data;
       int  id;
       int type;
    int  pay_type=PayPasswordView.NORMAT;
    View view;




    public static Fragment newInstance(int  id, int type) {
        ListViewOrderFragment fragment = new ListViewOrderFragment();
        Bundle args = new Bundle();
        args.putInt("id",id);
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();

            if (parent != null) {
                parent.removeView(view);
            }
        }
        id=getArguments().getInt("id");
        type=getArguments().getInt("type");
        switch (type)
        {
            case ORDER_ALL:
                orderType="all";
                break;
            case  ORDER_WAIT_PAY:
                //待付款
                orderType="7";
                break;
            case ORDER_WAIT_DEND:
                //待收货
                orderType="2";
                break;
            case ORDER_WAIT_SHOUHUO:
                //待评价
                orderType="3";
                break;
            case ORDER_WAIT_PINGJIA:
                //售后退款
                orderType="refund";
                break;
            case ORDER_WAIT_BACK:

                break;
        }
        toastUtil=new ToastUtil(baseActivity);
         view = inflater.inflate(R.layout.view_pull_listview  , null, false);
        listviw= (LaMaListView) view.findViewById(R.id.listviw);
        tv_nodata= (TextView) view.findViewById(R.id.tv_nodata);
        listviw.setPullLoadEnable(true);
        listviw.setPullRefreshEnable(true);
        listviw.setXListViewListener(new LaMaListView.IXListViewListener() {
            @Override
            public void onRefresh() {
               //刷新
                loadType=5;
                page=1;
                httpGetData();
            }

            @Override
            public void onLoadMore() {
                  //加载更多
                loadType=6;
                page++;
                httpGetData();
            }
        });
        listviw.setDivider(new ColorDrawable(Color.parseColor("#f2f2f2")));
        listviw.setDividerHeight(DensityUtil.dip2px(baseActivity,10));
        payDialog=new PayDialog(baseActivity);
        if (id<0)
        {
            titleManager=new TitleManager(view,baseActivity);
            titleManager.setTitleName("全部订单");
            httpGetData();
        }
       // initAdapter();
        if (baseActivity instanceof  OrderActivity)
        {
            ((OrderActivity)baseActivity).setNum(id,0);
//            if (id==0)
//            {
//                ((OrderActivity)baseActivity).setNum(id,0);
//            }else
//            {
//                ((OrderActivity)baseActivity).setNum(id,2);
//            }
        }
        return view;
    }



    private  void initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
        return;
        }

        commonBaseAdapter=new CommonBaseAdapter<OrderModel>(baseActivity,data,R.layout.adapter_order_static_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, final OrderModel item, int position)
            {
                helper.getView(R.id.ll_tuikuang).setVisibility(View.GONE);
                helper.getView(R.id.ll_pay).setVisibility(View.GONE);
                helper.getView(R.id.ll_wait_shouhuo).setVisibility(View.GONE);
                helper.getView(R.id.ll_wait_pingjia).setVisibility(View.GONE);
                helper.getView(R.id.ll_tuikuang).setVisibility(View.GONE);
                if (item.getOrder_status().equalsIgnoreCase("7"))
                {
                    //待付款
                    //待付款
                    helper.getView(R.id.ll_pay).setVisibility(View.VISIBLE);
                    Button bt_pay=   helper.getView(R.id.bt_pay);
                    bt_pay.setTag(item);
                    bt_pay.setOnClickListener(ListViewOrderFragment.this);
                    //判断是否允许支付
                    if (!TextUtils.isEmpty(item.getNonepay()) && item.getNonepay().equalsIgnoreCase("1"))
                    {
                        //不允许支付
                        bt_pay.setVisibility(View.INVISIBLE);
                    }else {
                        bt_pay.setVisibility(View.VISIBLE);
                    }
                }else  if(item.getOrder_status().equalsIgnoreCase("2"))
                {
                    //待收货
                    pay_type=PayPasswordView.PWS_SURE;
                    helper.getView(R.id.ll_wait_shouhuo).setVisibility(View.VISIBLE);
                    helper.getView(R.id.bt_shouhuo).setTag(item);
                    helper.getView(R.id.tv_tixing).setTag(item);
                    helper.getView(R.id.bt_shouhuo).setOnClickListener(ListViewOrderFragment.this);
                    helper.getView(R.id.tv_tixing).setOnClickListener(ListViewOrderFragment.this);
                }else if (item.getOrder_status().equalsIgnoreCase("3"))
                {
                    //待评价
                    helper.getView(R.id.bt_pingjia).setTag(item);
                    helper.getView(R.id.ll_wait_pingjia).setVisibility(View.VISIBLE);
                    helper.getView(R.id.bt_pingjia).setOnClickListener(ListViewOrderFragment.this);
                }else if (item.getOrder_status().equalsIgnoreCase("-1"))
                {
                    //用户取消的订单
                    helper.getView(R.id.ll_tuikuang).setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.bt_back)).setText(item.getWord_status());
                }
                else if(item.getOrder_status().equalsIgnoreCase("4")){
                    //售后退款
                    helper.getView(R.id.ll_tuikuang).setVisibility(View.VISIBLE);
                    helper.getView(R.id.bt_back).setOnClickListener(ListViewOrderFragment.this);
                    ((TextView) helper.getView(R.id.bt_back)).setText("售后/退款");
                }else {
                    helper.getView(R.id.ll_tuikuang).setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.bt_back)).setText("已完成");
                }
                //设置订单号
                helper.setText(R.id.tv_order_name,"订单号:"+item.getOrder_id());
                //日期
                helper.setText(R.id.tv_time,item.getOrder_at());

                //设置商品统计
                helper.setText(R.id.tv_count,"共"+item.getSum_nums()+"件商品 合计:");
                //价格
                helper.setText(R.id.tv_ship_all_meney,item.getMoney());
                //获取到listvie
                if (item.getGoods_detail()!=null && item.getGoods_detail().size()>0)
                {
                    ListView ll_child_lv=helper.getView(R.id.ll_child_lv);
                    ll_child_lv.setAdapter(new CommonBaseAdapter<ProModel>(baseActivity,item.getGoods_detail(),R.layout.order_item_adapter)
                    {
                        @Override
                        public void convert(CommonBaseViewHolder helper, ProModel childItem, int position)
                        {
                            helper.getView(R.id.ll_item).setTag(item);
                            helper.getView(R.id.ll_item).setOnClickListener(ListViewOrderFragment.this);
                            //设置商品内容
                            //图片
                            ImageView user_item_iv_avatar=helper.getView(R.id.user_item_iv_avatar);
                            baseActivity.imageLoader.displayImage(childItem.getGoods_pic(),user_item_iv_avatar);
                            //名称
                            helper.setText(R.id.tv_num,childItem.getGoods_title());
                            //价格
                            helper.setText(R.id.tv_meney,childItem.getPay_price());
                            //数量
                            helper.setText(R.id.number,"X"+childItem.getPay_nums());
                        }
                    });
                    //设置高度
                    Utility.setListViewHeightBasedOnChildren(ll_child_lv);
                }
            }
        };
        listviw.setAdapter(commonBaseAdapter);
    }
    String orderType="all";
    int page=1;

    public void onLoad() {
        listviw.stopRefresh();
        listviw.stopLoadMore();
    }

  private  void httpGetData()
  {
      if (data==null)
      {
          baseActivity.showDiaog();
      }
      baseActivity.params.clear();
      baseActivity.params.put("type",orderType);
      baseActivity.params.put("page",page+"");
      baseActivity.params.put("perpage","5");
      baseActivity.params.put("cust_id",baseActivity.application.getUserId());
      baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/orderList",baseActivity.params, OrderRespaone.class,new JsonObjectConve.HttpCallback<OrderRespaone>() {
          @Override
          public void onResponse(String s, OrderRespaone response, int code) {
              baseActivity.hideDialog();
              onLoad();
              if (response!=null && response.getData()!=null || data!=null)
              {
                  tv_nodata.setVisibility(View.GONE);
                //拿到订单数据
                  if (data!=null)
                  {
                      if (loadType==5)
                      {
                          loadType=6;
                          data.clear();
                      }
                      if (response.getData()!=null)
                      {
                          data.addAll(response.getData());
                      }

                  }else
                  {
                      data=response.getData();
                  }
                  if (data==null)
                  {
                      data=new ArrayList<OrderModel>();
                  }
                  initAdapter();

              }

              if (data==null || data.size()==0)
              {

                  //木有订单数据
                  //显示空数据页面
                  if (data==null || data.size()==0)
                  {
                      tv_nodata.setVisibility(View.VISIBLE);
                  }
              }
          }

          @Override
          public void onError(String msg) {
              onLoad();
              baseActivity.hideDialog();
              baseActivity.DisPlay(msg);
          }
      });
  }
    @Override
    public void onResume() {

      if (type==ORDER_WAIT_PAY  && data==null)
        {
             setUserVisibleHint(true);
          }
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
  if (baseActivity!=null)
  {
      if (isVisibleToUser )
      {
       //获取数据
          loadType=5;
          page=1;
          httpGetData();
      }
  }
    }
    ToastUtil toastUtil;
    private void showRemindToastView(String order_id) {

        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.params.put("order_id",order_id);
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/digOrder", baseActivity.params, LoginRespone.class,new JsonObjectConve.HttpCallback<LoginRespone>() {
            @Override
            public void onResponse(String s, LoginRespone response, int code) {
                if (response!=null)
                {
                    toastUtil.showTixing(baseActivity,"已提醒发货");
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
                baseActivity.hideDialog();
            }
        });

    }
    private  void httpPay(String password)
    {
        if (orderModel==null)
        {
            baseActivity.DisPlay("没有可以支付的订单!");
            return;
        }

        baseActivity.showDiaog();
        baseActivity.params.clear();
       baseActivity.params.put("order_id",orderModel.getOrder_id());
        baseActivity.params.put("money",orderModel.getMoney());
        baseActivity.params.put("channel",patStr);
//        baseActivity.params.put("tag","android");
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
                        baseActivity.DisPlay(response.getMsg()+"ssss");

                        return;
                    }
                    if (payTag.equalsIgnoreCase("ye"))
                    {
                        baseActivity.DisPlay(response.getMsg());
                        loadType=5;
                        page=1;
                        httpGetData();
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
    String  payTag="";
    String  patStr="";
    OrderModel orderModel;
    private View.OnClickListener patOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

   switch (view.getId())
   {
       case R.id.ll_add_new:
           //添加新卡
           Bundle bundle=new Bundle();
           bundle.putInt("branchType", BranhActivity.BRANCH_ADD_BANK);
           baseActivity.openActivity(BranhActivity.class,bundle);
           break;
       default:

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
           break;
   }
        }
    };


    @Override
    public void onClick(final View view) {
 switch (view.getId())
 {
     case R.id.ll_item:
         OrderModel item= (OrderModel) view.getTag();
         Bundle bundle=new Bundle();
         if (!TextUtils.isEmpty(item.getNonepay()))
         {
             bundle.putString("isPay",item.getNonepay());
         }else {
             bundle.putString("isPay","");
         }

         bundle.putInt("branchType",BranhActivity.BRANCH_ADD_BILL_DESCRIPT);
         bundle.putString("title","订单详情");
         bundle.putString("id",item.getOrder_id());
         bundle.putString("order_status",item.getOrder_status());
         baseActivity.openActivity(BranhActivity.class,bundle);
         return;
     case R.id.bt_ok:
         //评价确定
         //获取到星星

         View pingjia= (View) view.getTag();
         RatingBar app_ratingbar= (RatingBar) pingjia.findViewById(R.id.app_ratingbar);
         //评价内容
         EditTextWithDel et_content= (EditTextWithDel) pingjia.findViewById(R.id.et_content);
        //获取到评价  提交服务器
         httpPingJiaSubmit((OrderModel) pingjia.getTag(),et_content.getText().toString(),app_ratingbar.getRating()+"");
         mDialogWidget.dismiss();
         mDialogWidget=null;
         break;
     case R.id.bt_cancel:
         //评价取消
         mDialogWidget.dismiss();
         mDialogWidget=null;
         break;
     case R.id.bt_pay:
          //弹出付款界面
          orderModel= (OrderModel) view.getTag();
         if (TextUtils.isEmpty(payTag))
         {

             //弹出支付选择
             payDialog.showPayView(patOnClick);
         }else {
             mDialogWidget=new DialogWidget(baseActivity, getDecorViewDialog());
             mDialogWidget.show();
         }
         break;
     case R.id.tv_tixing:
         OrderModel obj= (OrderModel) view.getTag();
         showRemindToastView(obj.getOrder_id());
         break;
     case R.id.bt_shouhuo:
//         mDialogWidget=new DialogWidget(baseActivity, getDecorViewDialog());
//         mDialogWidget.show();
         //弹出对话框
         new AlertDialog(baseActivity).builder().setTitle("确认收货")
                 .setPositiveButton("确认", new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         OrderModel item= (OrderModel) view.getTag();
                         httpSureShouHuo(item.getOrder_id());
                     }
                 }).setNegativeButton("取消", new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         }).show();
         break;
     case R.id.bt_pingjia:
         OrderModel tagObj= (OrderModel) view.getTag();
         View pingjia_view=View.inflate(baseActivity,R.layout.dig_pingjia,null);
         //取消
         pingjia_view.setTag(tagObj);
         pingjia_view.findViewById(R.id.bt_cancel).setOnClickListener(this);
         pingjia_view.findViewById(R.id.bt_ok).setOnClickListener(this);
         pingjia_view.findViewById(R.id.bt_ok).setTag(pingjia_view);
         //确定
         mDialogWidget=new DialogWidget(baseActivity, pingjia_view);
         mDialogWidget.show();
         break;
     case R.id.bt_back:
         baseActivity.DisPlay("退款");
         break;
 }
    }


    private  void  httpPingJiaSubmit(final OrderModel tag,String  content,String judge_score )
 {
     baseActivity.params.clear();
     baseActivity.params.put("order_id",tag.getOrder_id());
     baseActivity.params.put("cust_id",baseActivity.application.getUserId());
     baseActivity.params.put("judge_content",content);
     baseActivity.params.put("judge_score",Float.valueOf(judge_score).intValue()+"");
     baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/judgeOrder",baseActivity.params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
         @Override
         public void onResponse(String s, CodeRespone response, int code) {
             if (response!=null)
             {
                 if (response.getCode()!=1)
                 {
                     baseActivity.DisPlay(response.getMsg());
                     return;
                 }
                 baseActivity.DisPlay(response.getMsg());
                 //移除
                 data.remove(tag);
                 initAdapter();
             }
         }

         @Override
         public void onError(String msg) {
             baseActivity.DisPlay(msg);
         }
     });


 }
    private void  httpSureShouHuo(String orderId)
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("order_id",orderId);
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"pay/setSubmit",baseActivity.params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
            @Override
            public void onResponse(String s, CodeRespone response, int code) {
                if (response!=null)
                {
                    baseActivity.hideDialog();
                    baseActivity.DisPlay(response.getMsg());
                    loadType=5;
                    page=0;
                    httpGetData();

                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        });
    }
    protected View getDecorViewDialog() {
        // TODO Auto-generated method stub
        return PayPasswordView.getInstance("商品详情",payTag,orderModel.getMoney(),baseActivity,new PayPasswordView.OnPayListener() {

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
