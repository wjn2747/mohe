package com.duolaguanjia.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.bean.CodeRespone;
import com.duolaguanjia.bean.LoginRespone;
import com.duolaguanjia.tool.CountTimer;
import com.duolaguanjia.view.EditTextWithDel;

/**
 * Created by Administrator on 2016/1/11.
 */
public class LoginActivity  extends BaseActivity  implements View.OnClickListener
{
   private EditTextWithDel et_longname;
    EditText et_pws;
    Button bt_next,bt_send_code;
    TextView tv_tuikuang_descript;
    @Override
    public void initView(Bundle savedInstanceState) {
          setContentView(R.layout.activity_login);
        et_longname= (EditTextWithDel) findViewById(R.id.et_longname);
        et_pws= (EditText) findViewById(R.id.et_pws);
        bt_send_code= (Button) findViewById(R.id.bt_send_code);
        bt_next= (Button) findViewById(R.id.bt_next);
        tv_tuikuang_descript= (TextView) findViewById(R.id.tv_tuikuang_descript);
        tv_tuikuang_descript.setOnClickListener(this);
        et_longname.addTextChangedListener(new TextChange());
        et_pws.addTextChangedListener(new TextChange());
        bt_next.setOnClickListener(this);
        bt_send_code.setOnClickListener(this);
        bt_send_code.setOnClickListener(this);
        et_longname.setFocusable(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return  true;
        }
        return  super.onKeyDown(keyCode, event);

    }

    @Override
    public void httpError(String code, String response) {

    }

  private void  login(String  code)
  {
      showDiaog();
      params.put("code",code);
      jsonObjectConve.httpPost(AppApplication.HOST+"user/checkLogin",params, LoginRespone.class,new JsonObjectConve.HttpCallback<LoginRespone>() {
          @Override
          public void onResponse(String s, LoginRespone response, int code) {
              hideDialog();
              if (response!=null)
              {
                  DisPlay(response.getMsg()+"用户 ID"+response.getData().getCust_id());
                //保存用户名
                  Util.putPreferenceString(LoginActivity.this,Util.SAVE_PHONE,et_longname.getText().toString().trim());
                  //保存用户ID
            Util.putPreferenceString(LoginActivity.this,Util.SAVE_UID,response.getData().getCust_id());
                  //设置是否绑定魔盒
                  if (response.getData().getIs_bd().equalsIgnoreCase("1"))
                  {
                      Util.putPreferenceBoolean(LoginActivity.this,Util.SAVE_BIND,true);
                  }else {
                      Util.putPreferenceBoolean(LoginActivity.this,Util.SAVE_BIND,false);
                  }

                  //保存支付密码
                  application.setPayPws(response.getData().getSet_pass());
            //打开主界面
            //直接进入主界面
                    openActivity(MainActivity.class);
                    finish();
              }else {
                  DisPlay("登陆失败!");
              }
          }

          @Override
          public void onError(String msg) {
              DisPlay(msg);
              hideDialog();
          }
      });


  }
    @Override
    public void onClick(View view) {
        if (view==tv_tuikuang_descript)
        {
            Bundle bundle=new Bundle();
            //用户协议
            bundle.putString("url", AppApplication.HOST+"user/userNotice");
            bundle.putString("title","用户协议");
            openProcessActivity(ProcessWebViewActivity.class,bundle);
        }
        if (view==bt_next)
        {
            //登录
            login(et_pws.getText().toString());
        }

  if (view==bt_send_code)
  {
      //验证码
      String phone=et_longname.getText().toString();
      if (TextUtils.isEmpty(phone))
      {
          DisPlay("请输入手机号");
          return;
      }
      //判断手机号码是否正确
      if (!Util.isMobileNum(phone))
      {
          DisPlay("请输入正确的手机号");
          return;
      }
      //发送验证码
      params.clear();
      params.put("mobile",phone);
      jsonObjectConve.httpPost(AppApplication.HOST+"user/getRegistCode",params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
          @Override
          public void onResponse(String s, CodeRespone response, int code) {
              if (response!=null && response.getCode()==1)
              {
                  DisPlay("验证码已发送!");
                  CountTimer timeCount= new CountTimer(bt_send_code);
                  timeCount.isShowBg(false);
                  timeCount.start();

              }else {
                  DisPlay(response.getMsg());
              }
          }

          @Override
          public void onError(String msg) {
              DisPlay(msg);
          }
      });

  }
    }

    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean Sign1 = et_longname.getText().length() > 0;
            boolean Sign2 = et_pws.getText().length() > 0;
            if (Sign1 && Sign2) {
                bt_next.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                bt_next.setEnabled(false);
            }
        }
    }
}
