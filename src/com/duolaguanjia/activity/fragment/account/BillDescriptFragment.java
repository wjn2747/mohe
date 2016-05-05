package com.duolaguanjia.activity.fragment.account;

import android.content.ComponentName;
import android.content.Intent;
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
import com.app_sdk.tool.ToastUtil;
import com.app_sdk.view.AlertDialog;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.activity.OrderActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.CodeRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.OrderModel;
import com.duolaguanjia.model.ProModel;
import com.duolaguanjia.respone.BackRespone;
import com.duolaguanjia.respone.PayRespone;
import com.duolaguanjia.respone.ShipRespone;
import com.duolaguanjia.tool.PayDialog;
import com.duolaguanjia.view.EditTextWithDel;
import com.duolaguanjia.view.WidgetInputItem;
import com.duolaguanjia.view.pay.DialogWidget;
import com.duolaguanjia.view.pay.PayPasswordView;
import com.pingplusplus.android.PaymentActivity;

/**
 * Created by Administrator on 2015/12/22.
 */
public class BillDescriptFragment extends BaseFragment  implements View.OnClickListener
{
    TitleManager titleManager;
    String type="";//状态
    String titleName;
    Button bt_ok,bt_shenqing,bt_queren;
    TextView tv_tuikuang_descript,tv_order_name,tv_time;
    WidgetInputItem wi_static,wi_order_no,wi_number,wi_all_money;
    LinearLayout ll_daishouhuo;
    ListView ll_child_lv;
    private DialogWidget mDialogWidget;
    PayDialog payDialog;
    int  pay_type=PayPasswordView.NORMAT;
    String  orderId;
    String  payTag="";
    String isPay;
    public static Fragment newInstance(String  id,String  order_status,String titleName,String  isPay) {
        BillDescriptFragment fragment = new BillDescriptFragment();
        Bundle args = new Bundle();
        args.putString("titleName",titleName);
        args.putString("order_status",order_status);
        args.putString("id",id);
        args.putString("isPay",isPay);
        fragment.setArguments(args);
        return fragment;
    }

    public static  String  getStaticNameById(String  stat)
    {
        if (stat.equalsIgnoreCase("refund"))
        {
            return "售后退款";
        }else if (stat.equalsIgnoreCase("7"))
        {
            return "待付款";
        }
        else if (stat.equalsIgnoreCase("2"))
        {
            return "待收货";
        }
        else if (stat.equalsIgnoreCase("8"))
        {
            return "待评价";
        }else if (stat.equalsIgnoreCase("3"))
        {
            return "订单已成功";
        }else if (stat.equalsIgnoreCase("-1"))
        {
            return "订单已取消";
        }else if (stat.equalsIgnoreCase("9"))
        {
            return "订单完成";
        }

        return "";
    }
    OrderModel data;
    private  void  httpGetData()
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity. params.put("order_id",orderId);
        baseActivity.params.put("cust_id",(Integer.valueOf(baseActivity.application.getUserId()))+"");
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/orderDetail",baseActivity.params, ShipRespone.class,new JsonObjectConve.HttpCallback<ShipRespone>() {
            @Override
            public void onResponse(String s, ShipRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null && response.getData()!=null)
                {
                    data=response.getData();
                    //数据填充
                    tv_time.setText(data.getOrder_at());
                    tv_order_name.setText(data.getOrder_id());
                    //订单号
                    wi_order_no.setRightTxt(data.getOrder_id());
                    //数量
                    wi_number.setRightTxt(data.getSum_nums());
                    //总金额
                    wi_all_money.setRightTxt(data.getMoney());
                    //订单状态
                    wi_static.setRightTxt(getStaticNameById(data.getOrder_status()));
                    //列表
                    ll_child_lv.setAdapter(new CommonBaseAdapter<ProModel>(baseActivity,response.getData().getGoods_detail(),R.layout.order_item_adapter) {
                        @Override
                        public void convert(CommonBaseViewHolder helper, ProModel childItem, int position) {
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

                }else {
                }
            }

            @Override
            public void onError(String msg)
            {
            baseActivity.hideDialog();
            baseActivity.DisPlay(msg);
            }
        });

    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type=getArguments().getString("order_status","");
        isPay=getArguments().getString("isPay","");
        titleName=getArguments().getString("titleName","账单详情");
        orderId=getArguments().getString("id","");
        toastUtil=new ToastUtil(baseActivity);
        httpGetData();
        View view = inflater.inflate(R.layout.fragment_order_descript  , null, false);
        bt_ok= (Button) view.findViewById(R.id.bt_ok);
        tv_time= (TextView) view.findViewById(R.id.tv_time);
        tv_order_name= (TextView) view.findViewById(R.id.tv_order_name);
        wi_order_no= (WidgetInputItem) view.findViewById(R.id.wi_order_no);
        wi_number= (WidgetInputItem) view.findViewById(R.id.wi_number);
        wi_all_money= (WidgetInputItem) view.findViewById(R.id.wi_all_money);
        ll_child_lv= (ListView) view.findViewById(R.id.ll_child_lv);
        bt_queren= (Button) view.findViewById(R.id.bt_queren);
        bt_shenqing= (Button) view.findViewById(R.id.bt_shenqing);
        ll_daishouhuo= (LinearLayout) view.findViewById(R.id.ll_daishouhuo);
        wi_static= (WidgetInputItem) view.findViewById(R.id.wi_static);
        tv_tuikuang_descript= (TextView) view.findViewById(R.id.tv_tuikuang_descript);
        tv_tuikuang_descript.setOnClickListener(this);
        payDialog=new PayDialog(baseActivity);
        bt_ok.setOnClickListener(this);
        bt_shenqing.setOnClickListener(this);
        bt_queren.setOnClickListener(this);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName(titleName );
        if (!TextUtils.isEmpty(type))
        {
            //订单列表进来的
            if (type.equalsIgnoreCase("7"))
            {
                //待付款
                wi_static.setVisibility(View.VISIBLE);
                bt_ok.setVisibility(View.VISIBLE);
                //判断是否允许支付     isPay
                //判断是否允许支付
                if (!TextUtils.isEmpty(isPay) && isPay.equalsIgnoreCase("1"))
                {
                    //不允许支付
                    bt_ok.setVisibility(View.INVISIBLE);
                }else {
                    bt_ok.setVisibility(View.VISIBLE);
                }

            }else  if(type.equalsIgnoreCase("2"))
            {
                //待收货
                //提醒卖家发货
                pay_type=PayPasswordView.PWS_SURE;
                wi_static.setVisibility(View.VISIBLE);
                ll_daishouhuo.setVisibility(View.VISIBLE);
                //确认收货
            }
            else if (type.equalsIgnoreCase("3"))
            {
                //待评价
                wi_static.setVisibility(View.VISIBLE);
                bt_ok.setText("评价");
                bt_ok.setVisibility(View.VISIBLE);
            }else if (type.equalsIgnoreCase("-1"))
            {
                //用户取消的订单

            }
            else if(type.equalsIgnoreCase("4")){
                //售后退款
                wi_static.setVisibility(View.VISIBLE);
                bt_ok.setText("取消退款");
                bt_ok.setVisibility(View.VISIBLE);
                tv_tuikuang_descript.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
     if (data==null)
     {
         return;
     }
        if (requestCode==50)
        {
            baseActivity.finish();
            return;
        }
        if (resultCode==30)
        {
       boolean bool=     data.getBooleanExtra("data",false);
            if (bool)
            {
                //取消成功
               baseActivity.finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view ==tv_tuikuang_descript)
        {
            //退款详情
            Bundle bundle=new Bundle();
            bundle.putString("id",data.getOrder_id());
            bundle.putInt("branchType",BranhActivity.BRANCH_ORDER_BACK_OK);
            Intent intent=new Intent(baseActivity,BranhActivity.class);
            intent.putExtras(bundle);
            baseActivity.startActivityForResult(intent,40);
            return;
        }

        if (view==bt_ok)
        {
            if (type.equalsIgnoreCase("7"))
            {
                //付款
                if (TextUtils.isEmpty(payTag))
                {
                    //弹出支付选择
                    payDialog.showPayView(patOnClick);
                }else {
                    mDialogWidget=new DialogWidget(baseActivity, getDecorViewDialog());
                    mDialogWidget.show();
                }
            }else  if (type.equalsIgnoreCase("2"))
            {
                //提醒发货
                showRemindToastView();
            }else if (type.equalsIgnoreCase("3"))
            {
                //评价
                View pingjia_view=View.inflate(baseActivity,R.layout.dig_pingjia,null);
                //取消
                pingjia_view.findViewById(R.id.bt_cancel).setOnClickListener(this);
                pingjia_view.findViewById(R.id.bt_ok).setOnClickListener(this);
                pingjia_view.findViewById(R.id.bt_ok).setTag(pingjia_view);
                //确定
                mDialogWidget=new DialogWidget(baseActivity, pingjia_view);
                mDialogWidget.show();
            }else if (type.equalsIgnoreCase("4"))
            {
                //退款
                httpRemoveT();

            }
        }
        else if (view.getId()==R.id.bt_ok)
        {
                //评价确定
                //获取到星星
                View pingjia= (View) view.getTag();
                RatingBar app_ratingbar= (RatingBar) pingjia.findViewById(R.id.app_ratingbar);
                //评价内容
                EditTextWithDel et_content= (EditTextWithDel) pingjia.findViewById(R.id.et_content);
                baseActivity.DisPlay("app_ratingbar"+app_ratingbar.getNumStars()+"et_content:"+et_content.getText().toString());
                mDialogWidget.dismiss();
                mDialogWidget=null;

        }else if (view.getId()==R.id.bt_cancel)
        {
            //评价取消
            mDialogWidget.dismiss();
            mDialogWidget=null;
        }
        else if (view==bt_queren)
        {
            //确认收货
            new AlertDialog(baseActivity).builder().setTitle("确认收货")
                    .setPositiveButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            httpSureShouHuo(data.getOrder_id());

                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();

        }else if (view==bt_shenqing)
        {
            //申请退款
            Bundle bundle=new Bundle();
            bundle.putString("id",data.getOrder_id());
            bundle.putString("money",data.getMoney());
            bundle.putInt("branchType",BranhActivity.BRANCH_ORDER_BACK);
            Intent intent=new Intent(baseActivity,BranhActivity.class);
            intent.putExtras(bundle);
            baseActivity.startActivityForResult(intent,33);
        }
    }
    /**
     * 取消退款
     */
    private  void httpRemoveT()
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.params.put("order_id",orderId);
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/rbackRefund", baseActivity.params, BackRespone.class,new JsonObjectConve.HttpCallback<BackRespone>() {
            @Override
            public void onResponse(String s, BackRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null )
                {
                    baseActivity.DisPlay(response.getMsg());
                    if (response.getCode()==1)
                    {
                        baseActivity.finish();
                        return;
                    }

                }else {
                    baseActivity.DisPlay("取消退款失败!");
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.hideDialog();
                baseActivity.DisPlay(msg);
            }
        });


    }

    ToastUtil toastUtil;
    private void showRemindToastView() {
        toastUtil.showTixing(baseActivity,"已提醒发货");
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
                    if (response.getCode()==1)
                    {
                        baseActivity.DisPlay(response.getMsg());
                        baseActivity.finish();
                    }else {
                        baseActivity.DisPlay("服务器忙，稍后再试！");
                    }

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
        return PayPasswordView.getInstance(data.getGoods_detail().get(0).getGoods_title(),payTag,data.getMoney(),baseActivity,new PayPasswordView.OnPayListener() {

            @Override
            public void onSurePay(String password) {
                // TODO Auto-generated method stub
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

    private  void httpPay(String password)
    {
        if (data==null)
        {
            baseActivity.DisPlay("没有可以支付的订单!");
            return;
        }

        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("order_id",data.getOrder_id());
        baseActivity.params.put("money",data.getMoney());
        baseActivity.params.put("channel",payTag);
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

                        baseActivity.DisPlay(response.getMsg());
                        baseActivity.finish();
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
                    payTag= (String) view.getTag();
                    //提交支付请求
                    mDialogWidget=new DialogWidget(baseActivity, getDecorViewDialog());
                    mDialogWidget.show();
                    payDialog.hideDialog();
                    break;

            }
        }
    };
}
