package com.duolaguanjia.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.app_sdk.view.xlistview.LaMaListView;
import com.duolaguanjia.R;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.model.ProBean;
import com.duolaguanjia.respone.ProDescriptRespone;
import com.duolaguanjia.view.ShangJiaDesciptIconSelectView;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/2.
 */
public class ShangJiaDescriptActivity extends BaseActivity  implements View.OnClickListener,LaMaListView.onScrollListener
{
   LaMaListView xlistviw;
    LinearLayout title;
    TextView tv_title_name;
    private CommonBaseAdapter commonBaseAdapter;
    ImageView iv_return;
    String id;
    @Override
    public void initView(Bundle savedInstanceState) {
        id=getIntent().getStringExtra("id");
        setContentView(R.layout.activity_descipt);
        xlistviw= (LaMaListView) findViewById(R.id.xlistviw);
        title= (LinearLayout) findViewById(R.id.title);
        tv_title_name= (TextView) findViewById(R.id.tv_title_name);
        iv_return= (ImageView) findViewById(R.id.iv_return);
       // title.getBackground().setAlpha(0);
        xlistviw.setPullLoadEnable(false);
        xlistviw.hideHeader();
        xlistviw.setDividerHeight(DensityUtil.dip2px(this,1));
        xlistviw.setDivider(new ColorDrawable(Color.parseColor("#D9D9D9")));
        xlistviw.setPullRefreshEnable(false);
        xlistviw.setScrollListener(this);
        iv_return.setOnClickListener(this);
        httpGetData(id);
        iv_return.setOnClickListener(this);
    }

    /**
     * 获取到商品详情数据
     */
    private  void httpGetData(String id)
    {
        params.clear();
        showDiaog();
        params.put("biz_id",id);
        jsonObjectConve.httpPost(AppApplication.HOST+"biz/bizDetail",params, ProDescriptRespone.class,new JsonObjectConve.HttpCallback<ProDescriptRespone>() {
            @Override
            public void onResponse(String s, ProDescriptRespone response, int code) {
                if (response!=null && response.getCode()==1)
                {
                    ProBean  proBean= response.getData();
                    //修改标题
                    tv_title_name.setText(TextUtils.isEmpty(proBean.getBiz_title())?"商家":proBean.getBiz_title());
                    //初始化
                    addTopBannder(proBean.getBiz_bg(),proBean.getBiz_intro());
                    addYunYingDescipt(proBean.getBiz_spec());
                   // addIcon();
                    initAdapter();

                }
            hideDialog();
            }

            @Override
            public void onError(String msg) {
                DisPlay(msg);
            }
        });

    }

    private void addIcon() {
        ShangJiaDesciptIconSelectView iconView=new ShangJiaDesciptIconSelectView(this);
        xlistviw.addHeaderView(iconView);
    }

    private void addYunYingDescipt(String  url)
    {
        View  yunYingDescript=View.inflate(this,R.layout.view_desciprt_info,null);
        final ImageView iv_guige= (ImageView) yunYingDescript.findViewById(R.id.iv_guige);
        imageLoader.loadImage(url,new SimpleImageLoadingListener()
        {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                iv_guige.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,loadedImage.getHeight()));
                iv_guige.setBackground(new BitmapDrawable(loadedImage));
            }
        });
        xlistviw.addHeaderView(yunYingDescript);
    }


    /**
     * 添加头部
     */
    private  void  addTopBannder(String pic,String desc)
    {
        View   top_bannder_view=View.inflate(this,R.layout.view_descipt_top_bannder,null);
        ImageView iv_pro_desc= (ImageView) top_bannder_view.findViewById(R.id.iv_pro_desc);
        imageLoader.displayImage(pic,iv_pro_desc);
        //详情
        TextView tv_desc= (TextView) top_bannder_view.findViewById(R.id.tv_desc);
        tv_desc.setText(desc);
        xlistviw.addHeaderView(top_bannder_view);
    }

    private  ArrayList<String >  descript_data=new ArrayList<>();
    private  void  initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
        return;
        }
        commonBaseAdapter=new CommonBaseAdapter<String>(this,descript_data,R.layout.adapter_descript_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, String item, int position)
            {
                if (position!=0)
                {
                    helper.getView(R.id.rl_title_descipt).setVisibility(View.GONE);
                }

            }
        };
        xlistviw.setAdapter(commonBaseAdapter);

    }

    @Override
    public void httpError(String code, String response) {

    }


    @Override
    public void onClick(View view) {
         if (view==iv_return)
         {
             finish();
         }
    }

    @Override
    public void onScroll(int scrollPosition) {
//        int headerHeight = xlistviw.getHeight() - title.getHeight();
//        float ratio = (float) Math.min(Math.max(scrollPosition, 0), headerHeight) / headerHeight;
//        int newAlpha =  (int)(ratio * 255);
//        title .getBackground().setAlpha(newAlpha);
    }
}
