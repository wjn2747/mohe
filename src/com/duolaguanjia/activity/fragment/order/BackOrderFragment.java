package com.duolaguanjia.activity.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.R;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.RefundModel;
import com.duolaguanjia.respone.BackRespone;
import com.duolaguanjia.view.BackMoneyLoding;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/26.
 */
public class BackOrderFragment extends BaseFragment  implements View.OnClickListener
{
    View   view;
    TitleManager titleManager;
    String id;
    Button bt_next;
    WidgetInputItem wi_yuanyin,wi_moey,wi_back_meney;
    BackMoneyLoding back_view;

    public static Fragment newInstance(String  id) {
        BackOrderFragment fragment = new BackOrderFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        id=getArguments().getString("id");
        view =inflater.inflate(R.layout.fragment_submit_back_ok, container, false);
        titleManager=new TitleManager(view,baseActivity);
        back_view= (BackMoneyLoding) view.findViewById(R.id.back_view);
        bt_next= (Button) view.findViewById(R.id.bt_next);
        wi_yuanyin= (WidgetInputItem) view.findViewById(R.id.wi_yuanyin);
        wi_back_meney= (WidgetInputItem) view.findViewById(R.id.wi_back_meney);
        wi_moey= (WidgetInputItem) view.findViewById(R.id.wi_moey);
        bt_next.setOnClickListener(this);
        titleManager.setTitleName("申请退款");
        httpGetDat();
        return view;
    }
    private RefundModel data;
    /**
     * 获取订单详情
     */
    private  void httpGetDat()
  {
      baseActivity.showDiaog();
      baseActivity.params.clear();
      baseActivity.params.put("cust_id",baseActivity.application.getUserId());
      baseActivity.params.put("order_id",id);
      baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/refundDetail", baseActivity.params, BackRespone.class,new JsonObjectConve.HttpCallback<BackRespone>() {
          @Override
          public void onResponse(String s, BackRespone response, int code) {
              baseActivity.hideDialog();
              if (response!=null && response.getCode()==1)
              {
                  data=response.getData();
                  //退款原因
                  wi_yuanyin.getEditText().setHint(data.getRefund_type());
                  //金额
                  wi_moey.getEditText().setHint(data.getRefund_mon());
                  //内容
                  wi_back_meney.getEditText().setHint(data.getRefund_content());
                  //设置进度
                  if (data.getOrder_status().equalsIgnoreCase("4"))
                  {
                      back_view.setStaticType(BackMoneyLoding.BackStatic.OK_CHULI);
                  }else if (data.getOrder_status().equalsIgnoreCase("5"))
                  {
                      back_view.setStaticType(BackMoneyLoding.BackStatic.BACK_SUCCESS);
                  }else if (data.getOrder_status().equalsIgnoreCase("6"))
                  {
                      back_view.setStaticType(BackMoneyLoding.BackStatic.BACK_FAIL);
                  }


              }else {
                  baseActivity.DisPlay("服务器忙!获取数据失败!");
              }
          }

          @Override
          public void onError(String msg) {
              baseActivity.hideDialog();
              baseActivity.DisPlay(msg);
          }
      });
  }
    /**
     * 取消退款
     */
    private  void httpRemoveT()
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.params.put("order_id",id);
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/rbackRefund", baseActivity.params, BackRespone.class,new JsonObjectConve.HttpCallback<BackRespone>() {
            @Override
            public void onResponse(String s, BackRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null )
                {
                    baseActivity.DisPlay(response.getMsg());
                    if (response.getCode()==1)
                    {
                        Intent intent=new Intent();
                        intent.putExtra("data",true);
                         baseActivity.setResult(30,intent);
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

    @Override
    public void onClick(View view) {
  if (view==bt_next)
  {
      //取消
      httpRemoveT();
  }
    }
}
