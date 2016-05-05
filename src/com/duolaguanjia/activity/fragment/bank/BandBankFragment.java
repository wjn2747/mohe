package com.duolaguanjia.activity.fragment.bank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.app_sdk.adapter.CommonBaseAdapter;
import com.app_sdk.adapter.CommonBaseViewHolder;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.BankEditText;

import java.util.ArrayList;

/**
 * Created by Night on 2015/12/15.
 */
public class BandBankFragment extends BaseFragment  implements View.OnClickListener
{
    TitleManager titleManager;
    ListView listviw;
    CommonBaseAdapter commonBaseAdapter;
    LinearLayout ll_add_bank;
    ArrayList<String> data=new ArrayList<String>();

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_bank  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("添加银行卡");
        listviw= (ListView) view.findViewById(R.id.listviw);
        ll_add_bank= (LinearLayout) view.findViewById(R.id.ll_add_bank);
        ll_add_bank.setOnClickListener(this);
        data.add("");
        data.add("");
        initAdapter();
        return view;
    }
    private  void  initAdapter()
    {
        if (commonBaseAdapter!=null)
        {
            commonBaseAdapter.notifyDataSetChanged();
        }
        commonBaseAdapter=new CommonBaseAdapter<String>(baseActivity,data,R.layout.adapter_bank_item)
        {
            @Override
            public void convert(CommonBaseViewHolder helper, String item, int position)
            {
                BankEditText et=   helper.getView(R.id.et_bank_no);
                et.setTag("4334123647899874");
                et.setText("4334123647899874");

            }
        };
        listviw.setAdapter(commonBaseAdapter);
    }



    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
         switch (view.getId())
         {
             case R.id.ll_add_bank:
                 //添加银行卡
                 bundle.putInt("branchType", BranhActivity.BRANCH_ADD_BANK);
                 break;

         }
        baseActivity.openActivity(BranhActivity.class,bundle);
    }
}
