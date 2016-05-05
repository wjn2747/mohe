package com.duolaguanjia.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BasePopupWindow;

import java.util.ArrayList;

/**
 * 条件选择
 * Created by Night on 2015/10/29.
 */
public class BackSelectPopWindow extends BasePopupWindow implements View.OnClickListener,AdapterView.OnItemClickListener
{
  ListView listviw;
    ArrayList<String>  data=new ArrayList<>();
    private CommonBaseAdapter commonBaseAdapter;
    public BackSelectPopWindow(Context context, int width, int height)
    {
        super(LayoutInflater.from(context).inflate(R.layout.pop_back
                , null), width, height,context);

    }
    public  void  setData( ArrayList<String>  data)
    {
        if (data!=null)
        {
            this.data.addAll(data);
            setCommonBaseAdapter();
        }


    }
    @Override
    public void initViews()
    {
        listviw= (ListView) findViewById(R.id.listviw);
        listviw.setOnItemClickListener(this);
    }
    public  void  setCommonBaseAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
            return;
        }
        commonBaseAdapter=new CommonBaseAdapter<String>(context,data, R.layout.adapter_back_item) {
            @Override
            public void convert(CommonBaseViewHolder helper, String item, int position) {
                helper.setText(R.id.tv_item,item);
            }
        };
        listviw.setAdapter(commonBaseAdapter);
    }
    @Override
    public void initEvents() {

    }

    @Override
    public void init() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        tv_tuikuang.setText(data.get(i));
        tv_tuikuang.setTag(i);
      dismiss();
    }
    TextView tv_tuikuang;

    public void setEditView(TextView tv_tuikuang)
    {
        this.tv_tuikuang=tv_tuikuang;
    }
}
