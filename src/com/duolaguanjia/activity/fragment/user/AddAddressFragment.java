package com.duolaguanjia.activity.fragment.user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.duolaguanjia.R;
import com.google.gson.Gson;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.CodeRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.AddressModel;
import com.duolaguanjia.respone.AddressRespone;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/31.
 */
public class AddAddressFragment  extends BaseFragment  implements AdapterView.OnItemClickListener,View.OnClickListener{
    public static  final  int REQUEST_ADDRESS=20;
    View view;
    ListView listviw;
    CommonBaseAdapter commonBaseAdapter;
    ArrayList<AddressModel> data;
    int  index=0;
    TitleManager titleManager;
    RelativeLayout rl_add;
    private String title;
    public static Fragment newInstance(String  title) {
        AddAddressFragment fragment = new AddAddressFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        title=getArguments().getString("title");
        view =inflater.inflate(R.layout.fragment_address, container, false);
        listviw= (ListView) view.findViewById(R.id.listviw);
        rl_add= (RelativeLayout) view.findViewById(R.id.rl_add);
        rl_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("branchType", BranhActivity.BRANCH_ADDRESS_ADD);
                bundle.putString("title","添加地址");
                bundle.putInt("type",5);
                bundle.putString("addressiD","1");
                baseActivity.openActivity(BranhActivity.class,bundle);
            }
        });
        int padding= DensityUtil.dip2px(baseActivity,10);
        listviw.setPadding(padding,padding,padding,padding);
        listviw.setDivider(new ColorDrawable(Color.parseColor("#F2F2F2")));
        listviw.setDividerHeight(padding);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName(TextUtils.isEmpty(title)?"添加地址":title);
        titleManager.showRightTxt("确定");
        titleManager.setRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//确定
                Intent intent=new Intent();
                intent.putExtra("data",new Gson().toJson(data.get(index)));
                //获取到当前选择的
                baseActivity.setResult(30,intent);
                //返回
                baseActivity.finish();
            }
        });
        listviw.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        httpGetAddress();
        super.onResume();
    }

    private  void httpGetAddress()
    {
        baseActivity.params.clear();
        baseActivity.showDiaog();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/addressList",baseActivity.params, AddressRespone.class,new JsonObjectConve.HttpCallback<AddressRespone>() {
            @Override
            public void onResponse(String s, AddressRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
   if (data!=null)
   {
       data.clear();
       data.addAll(response.getData());
   }else {

       data=response.getData();
   }

                    if (data==null ||data.size()==0)
                    {
                        baseActivity.DisPlay("没有地址信息！");
                    }else {
                       initAdapter();
                    }
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
                baseActivity. hideDialog();
            }
        });
    }
    private void initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
            return;
        }
        commonBaseAdapter=new CommonBaseAdapter<AddressModel>(baseActivity,data,R.layout.adapter_address) {
            @Override
            public void convert(CommonBaseViewHolder helper, AddressModel item, int position)
            {
                //设置默认地址
                 if (index==position)
                 {
                     helper.getView(R.id.adapter_address_default).setVisibility(View.VISIBLE);
                 }else {
                     helper.getView(R.id.adapter_address_default).setVisibility(View.GONE);
                 }
                //场所名称
                helper.setText(R.id.tv_style_name,item.getScene_title());
                //名字
                helper.setText(R.id.adapter_address_name,item.getReceive_name());
                //手机
                helper.setText(R.id.adapter_address_phone,item.getReceive_mobile());
                //省市区
                helper.setText(R.id.adapter_address_main_info,item.getProvice()+item.getCity()+item.getArea());
                //具体地址
                helper.setText(R.id.adapter_address_detail,item.getStreet());
                //编辑
                helper.getView(R.id.adapter_address_edit).setTag(item);
                helper.getView(R.id.adapter_address_edit).setOnClickListener(AddAddressFragment.this);
                //删除
                helper.getView(R.id.adapter_address_delete).setTag(item);
                helper.getView(R.id.adapter_address_delete).setOnClickListener(AddAddressFragment.this);

            }
        };
        listviw.setAdapter(commonBaseAdapter);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        index=i;
        commonBaseAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        AddressModel item= (AddressModel) view.getTag();
        switch (view.getId())
        {
            case R.id.adapter_address_delete:
                //删除
                httpDeleteAddress( item);
                break;
            case R.id.adapter_address_edit:
                //编辑
                Bundle bundle=new Bundle();
                bundle.putInt("branchType", BranhActivity.BRANCH_ADDRESS_ADD);
                bundle.putString("title","编辑地址");
                bundle.putInt("type",10);
                bundle.putString("addressiD",item.getAddress_id());
                baseActivity.openActivity(BranhActivity.class,bundle);
                break;
        }
    }
    private  void  httpDeleteAddress(final AddressModel item)
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.params.put("address_id",item.getAddress_id());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/siteRemove",baseActivity.params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
            @Override
            public void onResponse(String s, CodeRespone response, int code) {
                if (response!=null)
                {
                    baseActivity.DisPlay(response.getMsg());
                    baseActivity.hideDialog();
                    //清空
                    data.remove(item);
                    initAdapter();

                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        });

    }
}
