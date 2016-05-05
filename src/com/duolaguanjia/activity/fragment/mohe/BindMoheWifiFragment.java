package com.duolaguanjia.activity.fragment.mohe;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.broadcom.cooee.Cooee;
import com.duolaguanjia.R;
import com.litesuits.common.utils.HandlerUtil;
import com.duolaguanjia.activity.BindMoheActivity;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.server.WifiServer;
import com.duolaguanjia.tool.WifiUtil;
import com.duolaguanjia.view.EditTextWithDel;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/22.
 */
public class    BindMoheWifiFragment extends BaseFragment
{
    Button bt_next;
    WidgetInputItem wi_accout,wi_pws;
    EditTextWithDel accout_et,pws_et;
    WifiUtil wifiUtil;
    Thread mThread = null;
      boolean mDone = true;
    MyConn conn;
    WifiServer.MiddlePserson mp;

    //    String  runCode;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_mohe_wifi  , null, false);
        wifiUtil=new WifiUtil(baseActivity);
        wi_accout= (WidgetInputItem) view.findViewById(R.id.wi_accout);
        wi_pws= (WidgetInputItem) view.findViewById(R.id.wi_pws);
        accout_et= (EditTextWithDel) wi_accout.findViewById(R.id.et_input_content);
        pws_et= (EditTextWithDel) wi_pws.findViewById(R.id.et_input_content);
        accout_et.addTextChangedListener(new TextChange());
        pws_et.addTextChangedListener(new TextChange());
        bt_next= (Button) view.findViewById(R.id.bt_next);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baseActivity instanceof BindMoheActivity)
                {
                    //关闭软键盘
                      baseActivity.hideKey();
                //    baseActivity.showDiaog();
                    //提交wifi 数据
                    final String ssid=wi_accout.getEditText().getText().toString().trim();
                    final String password=wi_pws.getEditText().getText().toString().trim();
                    //生成唯一随机数
                  //  runCode=baseActivity.application.getUserMoble()+System.currentTimeMillis()+ RandomUtil.getRandom(1,9);
                    Cooee.SetPacketInterval(20);
                    mDone=true;
                    if (mThread == null)
                    {

                        Log.e("mDone","绑定mDone"+mDone);
                        mThread = new Thread() {
                            public void run() {
                                while (mDone)
                                {
                                    Log.e("sssss","绑定mDonessssss广播发送ing");
                                    Cooee.send(ssid, password, wifiUtil.mLocalIp);
                                }
                            }
                        };
                        mThread.start();
                        //baseActivity.DisPlay("正在连接wifi信号  请绑定魔盒!");
                       // ((BindMoheActivity)baseActivity).next(2);
                    }
                    //打开轮训服务
                    HandlerUtil.runOnUiThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            if (conn==null)
                            {
                                conn=new MyConn();
                                Intent intent=new Intent(baseActivity,WifiServer.class);
                                baseActivity.bindService(intent, conn, Context.BIND_AUTO_CREATE);
                            }
                        }
                    },2000);

                    baseActivity.showTxtDialog("正在连接wifi,请等待..");
                    baseActivity.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            //关闭
                            mDone=false;
                            mThread=null;
                            if (conn!=null)
                            {
                                baseActivity.unbindService(conn);
                            }
                            conn=null;

                        }
                    });
                   // postServerWifiStatic(runCode);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        if (conn!=null)
        {
            baseActivity.unbindService(conn);
            conn=null;
        }
        if (mp!=null)
        {
            mp.kill();
        }
        super.onDestroy();
    }

    public class MyConn implements ServiceConnection
    {
        //当服务链接的时候调用
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            mp=(WifiServer.MiddlePserson) arg1;
            //执行
            mp.callMes( new WifiServer.WifiConntent() {
                @Override
                public void onSuccess(String  mac) {
                    //成功
                    Log.e("xxxxxxxx","绑定成功!");
                    baseActivity.hideTxtDialog();
                    ((BindMoheActivity)baseActivity).mac=mac;
                    if ( ((BindMoheActivity)baseActivity).type==10)
                    {
                        baseActivity.DisPlay("wifi 配置成功!");
                        baseActivity.finish();
                        return;
                    }
                    baseActivity.DisPlay("绑定成功!");
                    //场景编辑
                    ((BindMoheActivity)baseActivity).next(2);
                }

                @Override
                public void onFail() {
                    baseActivity.hideTxtDialog();
                }
            }, ((BindMoheActivity)baseActivity).jiqiCode);
        }
        //服务失去链接的时候调用


        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            //服务被异常杀死的或者进程被kill的时候会调用


        }

    }
    @Override
    public void onStart() {
        super.onStart();
        String wifiName=  wifiUtil.getWifiName();
        if (!TextUtils.isEmpty(wifiName))
        {
            //获取到wifi
            //设置名称
            accout_et.setText(wifiName);
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
            boolean Sign1 = accout_et.getText().length() > 0;
            boolean Sign2 = pws_et.getText().length() > 0;
            if (Sign1 && Sign2) {
//                bt_next.setTextColor(0xFFFFFFFF);
                bt_next.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
//                bt_next.setTextColor(0xFFD0EFC6);
                bt_next.setEnabled(false);
            }
        }
    }
}
