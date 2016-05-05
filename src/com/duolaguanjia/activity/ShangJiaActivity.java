package com.duolaguanjia.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.view.xlistview.LaMaListView;
import com.duolaguanjia.R;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.CateModel;
import com.duolaguanjia.model.ProBean;
import com.duolaguanjia.respone.ProRespone;
import com.duolaguanjia.respone.ShangjiaRespone;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/27.
 */
public class ShangJiaActivity extends BaseActivity   implements View.OnClickListener,AdapterView.OnItemClickListener{
ListView lv_left;
    TextView tv_no_data;
    LaMaListView xlistviw;
    TitleManager titleManager;
    RelativeLayout rl_address;
    CommonBaseAdapter leftAdapter,rightAdapter;
    private ArrayList<CateModel> leftItemData=new ArrayList<>();
    private ArrayList<ProBean>   RightProdata=new ArrayList<>();
    int selectIndex=0;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shangjia);
        titleManager=new TitleManager(findViewById(R.id.ll_title),this);
        titleManager.setTitleName("商家");
        xlistviw= (LaMaListView) findViewById(R.id.xlistviw);
        xlistviw.setPullLoadEnable(false);
        xlistviw.setPullRefreshEnable(false);
        xlistviw.setXListViewListener(new LaMaListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //刷新
            }

            @Override
            public void onLoadMore() {
//加载更多
            }
        });
        tv_no_data= (TextView) findViewById(R.id.tv_no_data);
        rl_address= (RelativeLayout) findViewById(R.id.rl_address);
        rl_address.setOnClickListener(this);
        lv_left= (ListView) findViewById(R.id.lv_left);
        lv_left.setOnItemClickListener(this);
        xlistviw.setDivider(new ColorDrawable(Color.WHITE));
        xlistviw.setDividerHeight(5);
        rightAdapterInit();
        xlistviw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //商品详情
                Bundle bundle=new Bundle();
                bundle.putString("id",RightProdata.get(i-1).getBiz_id());
                openActivity(ShangJiaDescriptActivity.class,bundle);
            }
        });
        httpShangJiaData();
    }


    /**
     * 获取到右边商品列表
     */
    private   void  httpPproListData(final String   id)
    {
        if (TextUtils.isEmpty(id))
        {
            listAdapterInit();
            DisPlay("请输入商家编号");
            return;
        }
        params.clear();
        params.put("cate_id",id);
        jsonObjectConve.httpPost(AppApplication.HOST+"biz/showBiz",params, ProRespone.class,new JsonObjectConve.HttpCallback<ProRespone>() {
            @Override
            public void onResponse(String s, ProRespone response, int code) {
                hideDialog();
                if (response!=null &&  response.getData()!=null && response.getData().size()>0)
                {
                         RightProdata.addAll(response.getData());
                }
                rightAdapterInit();
                listAdapterInit();
            }

            @Override
            public void onError(String msg) {
                DisPlay(msg);
                hideDialog();
            }
        });

    }

    /**
     * 获取商家列表
     */
    private   void httpShangJiaData()
    {
        showDiaog();
        params.clear();
        jsonObjectConve.httpPost(AppApplication.HOST+"biz/slist",params, ShangjiaRespone.class,new JsonObjectConve.HttpCallback<ShangjiaRespone>() {
            @Override
            public void onResponse(String s, ShangjiaRespone response, int code) {
                hideDialog();
                if (response!=null &&  response.getData()!=null && response.getData().size()>0)
                {
                    leftItemData.addAll(response.getData());
                    //获取右边产品列表数据
                     httpPproListData(leftItemData.get(0).getCate_id());

                }else {
                    DisPlay("没有商家数据！");
                }
            }

            @Override
            public void onError(String msg) {
                DisPlay(msg);
                hideDialog();
            }
        });
    }

    private  void  rightAdapterInit()
    {
        if (RightProdata==null || RightProdata.size()==0)
        {

            tv_no_data.setVisibility(View.VISIBLE);
        }else {
            tv_no_data.setVisibility(View.GONE);
        }

        if (rightAdapter!=null)
        {
            rightAdapter.notifyDataSetChanged();
            return;
        }

        rightAdapter=new CommonBaseAdapter<ProBean>(this,RightProdata,R.layout.adapter_shangjia_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, ProBean item, int position)
            {
                //标题
                helper.setText(R.id.tv_title,item.getBiz_title());
                //快照
                imageLoader.displayImage(item.getBiz_kz(), (ImageView) helper.getView(R.id.iv_kuaizhao));
                //LOGO
                imageLoader.displayImage(item.getBiz_logo(), (ImageView) helper.getView(R.id.user_item_iv_avatar));
            }
        };
        xlistviw.setAdapter(rightAdapter);
    }
    private  void  listAdapterInit()
    {
        if (leftAdapter!=null)
        {
            leftAdapter.notifyDataSetChanged();
            return;
        }
        leftAdapter=new CommonBaseAdapter<CateModel>(this,leftItemData,R.layout.adapter_shangjia_left) {
            @Override
            public void convert(CommonBaseViewHolder helper, CateModel item, int position) {
                   helper.setText(R.id.tv_item_name,item.getCate_name());
                if (position==selectIndex)
                {
                    helper.getView(R.id.ll_item).setBackgroundColor(Color.WHITE);
                }else {
                    helper.getView(R.id.ll_item).setBackgroundColor(Color.parseColor("#f2f2f2"));
                }
            }
        };
        lv_left.setAdapter(leftAdapter);
    }

    @Override
    public void httpError(String code, String response) {

    }



    @Override
    public void onClick(View view) {
        if (view==rl_address)
        {
            //地址
            Bundle bundle=new Bundle();
            bundle.putInt("branchType",BranhActivity.BRANCH_USER_ADD_ADDRESS);
            openActivity(BranhActivity.class,bundle);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       //左边的item 点击
        CateModel cateModel= leftItemData.get(i);
        RightProdata.clear();
        //左边更新item
        selectIndex=i;
        listAdapterInit();
         //更新右边商品
        httpPproListData( cateModel.getCate_id());
    }
}
