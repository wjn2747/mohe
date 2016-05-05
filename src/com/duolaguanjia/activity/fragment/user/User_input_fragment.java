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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/30.
 */
public class User_input_fragment extends BaseFragment  implements View.OnClickListener,RadioGroup.OnCheckedChangeListener
{
    public static  final  int  INPUT_YUPE_NICK=5;//昵称
    public static  final  int  INPUT_YUPE_SEX=6;//性别
    public static  final  int  INPUT_YUPE_HANGYE=7;//行业
    public static  final  int  INPUT_YUPE_TITLE=8;//职业
    TitleManager  titleManager;
    Button bt_ok;
    int type_id=INPUT_YUPE_NICK;
    RadioGroup rg_sex;
    RadioButton cb_nan,cb_nv;
    WidgetInputItem wi_input;
    RelativeLayout rl_sex;
    String  sex="";
    String value;
    public static Fragment newInstance(int  type_id,String value) {
        User_input_fragment fragment = new User_input_fragment();
        Bundle args = new Bundle();
        args.putInt("type_id",type_id);
        args.putString("value",value);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type_id=getArguments().getInt("type_id",INPUT_YUPE_NICK);
        value=getArguments().getString("value");
        View view = inflater.inflate(R.layout.fragment_input_item  , null, false);
        rg_sex= (RadioGroup) view.findViewById(R.id.rg_sex);
        rl_sex= (RelativeLayout) view.findViewById(R.id.rl_sex);
        rg_sex.setOnCheckedChangeListener(this);
            cb_nan= (RadioButton) view.findViewById(R.id.cb_nan);
        cb_nv= (RadioButton) view.findViewById(R.id.cb_nv);
        bt_ok= (Button) view.findViewById(R.id.bt_ok);
        wi_input= (WidgetInputItem) view.findViewById(R.id.wi_input);
        bt_ok.setOnClickListener(this);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("昵称");
        //设置值
        if (!TextUtils.isEmpty(value))
        {
            wi_input.getEditText().setText(value);
        }
        switch (type_id)
        {
            case INPUT_YUPE_SEX:
                //性别
                titleManager.setTitleName("性别");
                rl_sex.setVisibility(View.VISIBLE);
                wi_input.setVisibility(View.GONE);
                if (value.equalsIgnoreCase("男"))
                {
                    cb_nan.setChecked(true);
                }else if (value.equalsIgnoreCase("女")){
                    cb_nv.setChecked(true);
                }
                break;
            case INPUT_YUPE_TITLE:
                //职业
                titleManager.setTitleName("职业");
                wi_input.getEditText().setHint("请输入您的职业");
                wi_input.setLeftTxt("职业");
                break;
        }
        return view;
    }


    @Override
    public void onClick(View view) {
         if (view==bt_ok)
         {
             //OK
            String str= wi_input.getEditText().getText().toString();
             if (!TextUtils.isEmpty(sex))
             {
                 resuleValue(sex);
                 return;
             }
             if (TextUtils.isEmpty(str))
             {
                 baseActivity.DisPlay("请输入内容");
                 return;
             }
             resuleValue(str);
         }
    }
private void resuleValue(String  value){
    Intent intent=new Intent();
    intent.putExtra("value",value);
    baseActivity.setResult(Activity.RESULT_OK,intent);
    baseActivity.finish();
}
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
  if (i==R.id.cb_nan)
  {
      sex="男";
  }else if (i==R.id.cb_nv)
  {
      sex="女";
  }
    }
}
