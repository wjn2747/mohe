package com.duolaguanjia.activity.fragment.mohe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.bean.CodeRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.tool.CountTimer;
import com.duolaguanjia.view.EditTextWithDel;
import com.duolaguanjia.view.WidgetInputItem;
import com.duolaguanjia.view.pay.DialogWidget;

/**
 * Created by Administrator on 2015/12/23.
 */
public class DeleteBindMoHeFragment extends BaseFragment  implements View.OnClickListener
{
    Button bt_jiebang;
    TitleManager titleManager;
    private DialogWidget mDialogWidget;
    Button bt_send_code;
    WidgetInputItem wi_mohe_list,wi_yanzm;
    EditTextWithDel et_phone;
    String  code;
    public static Fragment newInstance(String  id) {
        DeleteBindMoHeFragment fragment = new DeleteBindMoHeFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }
    private  void  httpGetCode(String phone)
    {
        baseActivity.params.clear();
        baseActivity.params.put("mobile",phone);
        baseActivity. jsonObjectConve.httpPost(AppApplication.HOST+"user/getRegistCode", baseActivity.params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
            @Override
            public void onResponse(String s, CodeRespone response, int code) {
                if (response!=null && response.getCode()==1)
                {
                    baseActivity.DisPlay("验证码已发送!");
                    CountTimer timeCount= new CountTimer(bt_send_code);
                    timeCount.isShowBg(false);
                    timeCount.start();
                }else {
                   baseActivity. DisPlay(response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        });
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        code=getArguments().getString("id");
        View view = inflater.inflate(R.layout.fragment_bind_mohe_jiebang  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        wi_mohe_list= (WidgetInputItem) view.findViewById(R.id.wi_mohe_list);
        wi_yanzm= (WidgetInputItem) view.findViewById(R.id.wi_yanzm);
        bt_send_code= (Button) view.findViewById(R.id.bt_send_code);
        wi_mohe_list.setText(code);
        bt_send_code.setOnClickListener(this);
        titleManager.setTitleName("解绑");
        et_phone= (EditTextWithDel) view.findViewById(R.id.et_phone);
        bt_jiebang= (Button) view.findViewById(R.id.bt_jiebang);
        bt_jiebang.setOnClickListener(this);
        et_phone.setHintTextColor(Color.argb(110, 153, 153, 153));
        return view;
    }
// private ArrayList<String> data=new ArrayList<>();

    @Override
    public void onClick(View view) {
         switch (view.getId())
         {
             case R.id.bt_send_code:
                 //验证码
                 String phone=et_phone.getText().toString();
                 if (TextUtils.isEmpty(phone))
                 {
                     baseActivity.DisPlay("请输入手机号");
                     return;
                 }
                 //判断手机号码是否正确
                 if (!Util.isMobileNum(phone))
                 {
                     baseActivity.DisPlay("请输入正确的手机号");
                     return;
                 }

                 httpGetCode(phone);
                 break;
             case R.id.bt_jiebang:
                 if (TextUtils.isEmpty(code))
                 {
                     baseActivity.DisPlay("请输入绑定码!");
                     return;
                 }
                 //验证码
               String yanzm=wi_yanzm.getText().toString();
                 if (TextUtils.isEmpty(yanzm))
                 {
                     baseActivity.DisPlay("请输入验证码!");
                     return;
                 }
                 baseActivity.showDiaog();
                 baseActivity.params.clear();
                 baseActivity.params.put("bdcode",code);
                 baseActivity.params.put("code",yanzm);
                 baseActivity.params.put("mobile",et_phone.getText().toString());
                 baseActivity.params.put("cust_id",baseActivity.application.getUserId());
                 //发送
                 baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/unbindBox",baseActivity.params, BaseRespone.class,new JsonObjectConve.HttpCallback<BaseRespone>() {
                     @Override
                     public void onResponse(String s, BaseRespone response, int code) {
                         baseActivity.hideDialog();
                         if (response!=null )
                         {
                             if (response.getCode()!=1)
                             {
                                 baseActivity. DisPlay(response.getMsg());
                                 return;
                             }
                             //解绑成功
                             baseActivity.finish();

                         }else {
                             baseActivity. DisPlay("解绑失败");
                         }
                     }

                     @Override
                     public void onError(String msg) {
                         baseActivity.hideDialog();
                         baseActivity. DisPlay(msg);
                     }
                 });


             break;
             case R.id.wi_mohe_list:
//                 View select_mohe_view=View.inflate(baseActivity,R.layout.dig_listview,null);
//                 ListView listviw= (ListView) select_mohe_view.findViewById(R.id.listviw);
//                 listviw.setAdapter(new CommonBaseAdapter<String>(baseActivity,data,R.layout.adapter_delete_mohe_item) {
//                     @Override
//                     public void convert(CommonBaseViewHolder helper, String item, int position) {
//
//                     }
//                 });
//                 mDialogWidget=new DialogWidget(baseActivity, select_mohe_view);
//                 mDialogWidget.setOutSideTouch(true);
//                 mDialogWidget.show();
                 break;

         }
    }
}
