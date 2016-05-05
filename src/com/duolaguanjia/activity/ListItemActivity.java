package com.duolaguanjia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.bean.SelectValueBean;
import com.duolaguanjia.manager.TitleManager;

import java.util.ArrayList;

/**
 * Created by Night on 2015/10/9.
 */
public class ListItemActivity extends BaseActivity
{
    ListView listviw;
    String   title;
    TitleManager titleManager;
    private ArrayList<SelectValueBean> data=null;

    @Override
    public void initView(Bundle savedInstanceState) {
       setContentView(R.layout.activity_item_view);
        titleManager=new TitleManager(findViewById(R.id.ll_title),this);
        title=getIntent().getStringExtra("title");
        listviw= (ListView) findViewById(R.id.listviw);
        titleManager.setTitleName(title);
        if (getIntent().getSerializableExtra("data")==null)
        {
            finish();
            return;
        }
        data= (ArrayList<SelectValueBean>) getIntent().getSerializableExtra("data");
        if (data==null)
        {
            finish();
            return;
        }
        listviw.setAdapter(new CommonBaseAdapter<SelectValueBean>(this,data,R.layout.adapter_keshi_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, SelectValueBean item, int position) {
                helper.setText(R.id.tv_shengfen_count,item.getName());
            }
        });
        listviw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                intent =new Intent();
                intent.putExtra("data", data.get(i));
                setResult(Activity.RESULT_OK,intent);
                ListItemActivity.this.finish();
            }
        });
    }

    @Override
    public void httpError(String code, String response) {

    }

}
