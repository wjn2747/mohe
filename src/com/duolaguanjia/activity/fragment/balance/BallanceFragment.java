package com.duolaguanjia.activity.fragment.balance;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.UserInfoRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.BankEditText;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/18.
 */
public class BallanceFragment extends BaseFragment  implements View.OnClickListener
{
   int  type=10; //有银行卡 5  没有银行卡  10
    LinearLayout ll_add_bank;
    RelativeLayout tv_bank;
//    Button bt_chongzhi;
    ListView listviw;
    TextView tv_no_bank,tv_zhanghuyue,et_keyong,et_dongjie,tv_meney;
    CommonBaseAdapter commonBaseAdapter;
    ArrayList<String> data=new ArrayList<String>();
    TitleManager titleManager;



    private void httpGetData()
    {
        baseActivity.showDiaog();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/personEdit",baseActivity.params, UserInfoRespone.class,new JsonObjectConve.HttpCallback<UserInfoRespone>() {
            @Override
            public void onResponse(String s, UserInfoRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null&& response.getData()!=null)
                {
                    //余额
                    tv_meney.setText(response.getData().getMon()+"");
                    et_keyong.setText(response.getData().getMon()+"");
                }
            }

            @Override
            public void onError(String msg) {
            }
        });
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("余额");
         titleManager.showRightTxt("交易明细");
        titleManager.setRightOnClick(this);
        tv_bank= (RelativeLayout) view.findViewById(R.id.tv_bank);
        listviw= (ListView) view.findViewById(R.id.listviw);
        listviw.setVisibility(View.GONE);
        tv_meney= (TextView) view.findViewById(R.id.tv_meney);
        et_keyong= (TextView) view.findViewById(R.id.et_keyong);
        listviw.setPadding(DensityUtil.dip2px(baseActivity,10),0,DensityUtil.dip2px(baseActivity,10),0);
        listviw.setDivider(new ColorDrawable(Color.WHITE));
        listviw.setDividerHeight(DensityUtil.dip2px(baseActivity,10));
        ll_add_bank= (LinearLayout) view.findViewById(R.id.ll_add_bank);
        ll_add_bank.setVisibility(View.GONE);
        tv_no_bank= (TextView) view.findViewById(R.id.tv_no_bank);
        et_dongjie= (TextView) view.findViewById(R.id.et_dongjie);
//        bt_chongzhi= (Button) view.findViewById(R.id.bt_chongzhi);
        et_keyong= (TextView) view.findViewById(R.id.et_keyong);
        tv_zhanghuyue= (TextView) view.findViewById(R.id.tv_zhanghuyue);
        tv_zhanghuyue.setTextColor(Color.argb(120,255,255,255));
        ll_add_bank.setOnClickListener(this);
        //bt_chongzhi.setOnClickListener(this);
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        initAdapter();
        httpGetData();
        setBankIsShow(5);
        return view;
    }

   private  void initAdapter()
   {
       if (commonBaseAdapter!=null)
       {
           commonBaseAdapter.notifyDataSetChanged();
       }
       commonBaseAdapter=new CommonBaseAdapter<String>(baseActivity,data,R.layout.adapter_bank_item)
       {
           @Override
           public void convert(CommonBaseViewHolder helper, String item, int position) {
               BankEditText et=   helper.getView(R.id.et_bank_no);
               et.setTag("4334123647899874");
               et.setText("4334123647899874");
           }
       };
       listviw.setAdapter(commonBaseAdapter);
   }
    public  void setBankIsShow(int  type)
    {
        switch (type)
        {
            case 5:
                //有银行卡
                tv_no_bank.setVisibility(View.GONE);
                tv_bank.setVisibility(View.VISIBLE);
                et_dongjie.setTextColor(baseActivity.getResources().getColor(R.color.red));
                et_keyong.setTextColor(baseActivity.getResources().getColor(R.color.red));
                //显示充值
//                bt_chongzhi.setVisibility(View.VISIBLE);
                break;
            case 10:
                //没有银行卡
                tv_no_bank.setVisibility(View.VISIBLE);
                tv_bank.setVisibility(View.GONE);
                et_dongjie.setTextColor(baseActivity.getResources().getColor(R.color.hint));
                et_keyong.setTextColor(baseActivity.getResources().getColor(R.color.hint));
                //隐藏充值
//                bt_chongzhi.setVisibility(View.GONE);
                break;

        }

    }



    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        switch (view.getId())
        {
            case R.id.ll_add_bank:
                //添加
                bundle.putInt("branchType", BranhActivity.BRANCH_ADD_BANK);
                baseActivity.openActivity(BranhActivity.class,bundle);
                break;
            case R.id.tv_right_button:
                //交易明细
                bundle.putInt("branchType",BranhActivity.BRANCH_ADD_BILL);
                bundle.putString("title","交易明细");
                baseActivity.openActivity(BranhActivity.class,bundle);
                break;
//            case R.id.bt_chongzhi:
//                //充值
//             bundle.putInt("branchType", BranhActivity.BRANCH_ADD_BILLRECHARGE);
//               baseActivity.openActivity(BranhActivity.class,bundle);
//                break;
        }
    }
}
