package com.duolaguanjia.activity.fragment.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.R;
import com.google.gson.Gson;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.activity.CaptureActivity;
import com.duolaguanjia.activity.fragment.mohe.BindMoheZxingFragment;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.AddressModel;
import com.duolaguanjia.model.GongSiAddressModel;
import com.duolaguanjia.respone.GongSiAddressRespone;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2016/1/5.
 */
public class ShenHeSuccessAddressFragment extends BaseFragment   implements View.OnClickListener
{
    TextView tv_add,tv_phone,tv_gongsi,tv_style_name,adapter_address_name,adapter_address_phone,adapter_address_main_info,adapter_address_detail;
    String  mac;
    WidgetInputItem wi_address_descript;
    RelativeLayout rl_add;
    Button bt_zxing;
    TitleManager titleManager;
    LinearLayout ll_address_item;
    Button bt_ok;
    String applyId;
    public static Fragment newInstance(String  mac,String  applyId ) {
        ShenHeSuccessAddressFragment fragment = new ShenHeSuccessAddressFragment();
        Bundle args = new Bundle();
        args.putString("mac",mac);
        args.putString("applyId",applyId );
        fragment.setArguments(args);
        return fragment;
    }
    private  void httpPutWeiXiu()
    {String  code=wi_address_descript.getEditText().getText().toString();
        if (TextUtils.isEmpty(code))
        {

            baseActivity.DisPlay("请输入寄回运单号!");
            return;
        }
        //地址
        if (addressModel==null)
        {
            baseActivity.DisPlay("请选择地址!");
            return;
        }
        baseActivity.params.clear();
        baseActivity.showDiaog();
        baseActivity.params.put("applyId",applyId);
        baseActivity.params.put("express",code);
        baseActivity.params.put("address_id",addressModel.getAddress_id());
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"refund/putReback",baseActivity.params, BaseRespone.class,new JsonObjectConve.HttpCallback<BaseRespone>() {
            @Override
            public void onResponse(String s, BaseRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    baseActivity.DisPlay(response.getMsg());
                    if (response.getCode()==1)
                    {
                        baseActivity.finish();

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
    View view;
    GongSiAddressModel data;
    private  void httpGetGongSiAddress()
    {
        baseActivity.params.clear();
        baseActivity.showDiaog();
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"refund/chAddress",baseActivity.params, GongSiAddressRespone.class,new JsonObjectConve.HttpCallback<GongSiAddressRespone>() {
            @Override
            public void onResponse(String s, GongSiAddressRespone response, int code) {
                baseActivity. hideDialog();
                if (response!=null)
                {
                    if (response.getCode()==1)
                    {
                        data=response.getData();
                        //设置收件人
                        tv_gongsi.setText(data.getName());
                        //联系方式
                        tv_phone.setText(data.getMobile());
                        //联系地址
                        tv_add.setText(data.getAddress());

                    }
                }
            }
            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        });
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mac=getArguments().getString("mac");
        applyId=getArguments().getString("applyId");
        View view = inflater.inflate(R.layout.fragment_success_address  , null, false);
        view.findViewById(R.id.ll_address_item).setVisibility(View.GONE);
        bt_zxing= (Button) view.findViewById(R.id.bt_zxing);
        bt_ok= (Button) view.findViewById(
                R.id.bt_ok
        );
        bt_ok.setOnClickListener(this);
        bt_zxing.setOnClickListener(this);
        httpGetGongSiAddress();
        tv_style_name= (TextView) view.findViewById(R.id.tv_style_name);
        tv_phone= (TextView) view.findViewById(R.id.tv_phone);
        tv_add= (TextView) view.findViewById(R.id.tv_add);
        adapter_address_name= (TextView) view.findViewById(R.id.adapter_address_name);
        wi_address_descript= (WidgetInputItem) view.findViewById(R.id.wi_address_descript);
        adapter_address_phone= (TextView) view.findViewById(R.id.adapter_address_phone);
        adapter_address_main_info= (TextView) view.findViewById(R.id.adapter_address_main_info);
        adapter_address_detail= (TextView) view.findViewById(R.id.adapter_address_detail);
        tv_gongsi= (TextView) view.findViewById(R.id.tv_gongsi);
        ll_address_item= (LinearLayout) view.findViewById(R.id.ll_address_item);
        ll_address_item.setVisibility(View.GONE);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("维修");
        rl_add= (RelativeLayout) view.findViewById(R.id.rl_add);
        rl_add.setOnClickListener(this);
        return view;
    }
    AddressModel addressModel;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data==null)
        {
            return;
        }
        if (requestCode==BindMoheZxingFragment.SCANNIN_GREQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getBundleExtra("bundle");
                String resultString = bundle.getString("result");
                //设置
                wi_address_descript.getEditText().setText(resultString);
            }
            return;
        }
        if ( data.getStringExtra("data")!=null)
        {
            try {
                 addressModel=new Gson().fromJson(data.getStringExtra("data"),AddressModel.class);
                if (addressModel!=null)
                {
                    //设置
                    ll_address_item.setVisibility(View.VISIBLE);
                    rl_add.setVisibility(View.GONE);
                    //设置
                    //场所名称
                    tv_style_name.setText(addressModel.getScene_title());
                    //名字
                    adapter_address_name.setText(addressModel.getReceive_name());
                    //手机
                    adapter_address_phone.setText(addressModel.getReceive_mobile());
                    //省市区
                    adapter_address_main_info.setText(addressModel.getProvice()+addressModel.getCity()+addressModel.getArea());
                    //具体地址
                    adapter_address_phone.setText(addressModel.getReceive_mobile());
                }
            }catch (Exception e)
            {

            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(baseActivity,BranhActivity.class);
       switch (view.getId())
       {
           case R.id.rl_add:
               intent.putExtra("branchType", BranhActivity.BRANCH_USER_ADD_ADDRESS);
               break;
           case R.id.bt_ok:
               httpPutWeiXiu();
               return;
           case R.id.bt_zxing:
               Intent intent1 = new Intent();
               intent1.setClass(baseActivity,
                       CaptureActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               baseActivity.startActivityForResult(intent1,
                       BindMoheZxingFragment.SCANNIN_GREQUEST_CODE);
               return;
       }
        baseActivity.startActivityForResult(intent,AddAddressFragment.REQUEST_ADDRESS);
    }
}
