package com.duolaguanjia.activity.fragment.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.R;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.BillModel;
import com.duolaguanjia.respone.BillsRespone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/18.
 */
public class BillFragment extends BaseFragment  implements AdapterView.OnItemClickListener
{
    TitleManager titleManager;
    private CommonBaseAdapter commonBaseAdapter;
    ListView listviw;
    TextView tv_no_data;
    ArrayList<BillModel> data=new ArrayList<>();
    HashMap<String, ArrayList<BillModel>> city_hash_map=new HashMap<String, ArrayList<BillModel>>();
String  title;

    public static Fragment newInstance(String   title) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        title=getArguments().getString("title");
        View view = inflater.inflate(R.layout.title_listview  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("账单");
        if (!TextUtils.isEmpty(title))
        {
            titleManager.setTitleName(title);
        }
        listviw= (ListView) view.findViewById(R.id.listviw);
        tv_no_data= (TextView) view.findViewById(R.id.tv_no_data);
        listviw.setOnItemClickListener(this);
        httpGetData();
        return view;
    }
    private  void httpGetData()
    {
        baseActivity.params.clear();
        baseActivity.showDiaog();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.params.put("perpage","100");
        baseActivity.params.put("page","1");
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/myBills",baseActivity.params, BillsRespone.class,new JsonObjectConve.HttpCallback<BillsRespone>() {
            @Override
            public void onResponse(String s, BillsRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    if (response.getCode()==1)
                    {
                        if (response.getData()==null || response.getData().size()==0)
                        {
                            tv_no_data.setVisibility(View.VISIBLE);
                            return;
                        }
                        tv_no_data.setVisibility(View.GONE);
                        HashMap<String,ArrayList<BillModel>> ddddd=new HashMap<String, ArrayList<BillModel>>();
                        for (BillModel model:  response.getData())
                        {
                            String  month=Integer.valueOf(model.getMonth())+"月";
                            if ( ddddd.get(month)==null)
                            {
                                ddddd.put(month,new ArrayList<BillModel>());
                            }
                            ddddd.get(month).add(model);
                        }
                        //遍历HASH
                        Iterator iter = ddddd.entrySet().iterator();
                        data.clear();
                        while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                                String key = entry.getKey().toString();
                                 ArrayList<BillModel> val = (ArrayList<BillModel>) entry.getValue();
                                    val.get(0).setShowMon(true);
                            data.addAll(val);
                        }

                        //获取到账单列表
                        initAdpter();
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

    private void initAdpter() {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
            return;
        }
        commonBaseAdapter=new CommonBaseAdapter<BillModel>(baseActivity,data,R.layout.adapter_bill_item)
        {
            @Override
            public void convert(CommonBaseViewHolder helper, BillModel item, int position)
            {
                //判断上一个显示
                String  mm=Integer.valueOf(item.getMonth())+"月";
                if (item.isShowMon())
                {
                    //显示
                    helper.getView(R.id.ll_time_item).setVisibility(View.VISIBLE);
                }else {
                    //隐藏标题
                    helper.getView(R.id.ll_time_item).setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(title))
                {
                    helper.getView(R.id.ll_time_item).setVisibility(View.GONE);
                }
                ImageView iv_icon= helper.getView(R.id.iv_icon);
                TextView tv_price=helper.getView(R.id.tv_price);
                //判断收入支出
                if (item.getFinance_money().contains("-"))
                {
                    //支出
                    iv_icon.setImageResource(R.drawable.pay);
                    tv_price.setTextColor(Color.parseColor("#1da84e"));
                }else {
                    //收入
                    iv_icon.setImageResource(R.drawable.shou);
                    tv_price.setTextColor(Color.parseColor("#ff9c61"));
                }
                //名字
                helper.setText(R.id.tv_order_name,item.getMoney_remark());
                //日期
                helper.setText(R.id.tv_time,item.getAdd_at());
                //价格
                helper.setText(R.id.tv_price,item.getFinance_money());
                //月份
              helper.setText(R.id.tv_monetg,mm);
            }
        };
        listviw.setAdapter(commonBaseAdapter);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
//        Bundle bundle=new Bundle();
//        bundle.putInt("branchType", BranhActivity.BRANCH_ADD_BILL_DESCRIPT);
//        bundle.putString("title","账单详情");
//         baseActivity.openActivity(BranhActivity.class,bundle);
    }
}
