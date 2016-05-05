package com.duolaguanjia.server;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.respone.MacRespone;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/25.
 */
public class WifiServer extends Service
{
    Map<String, String> params;
    boolean mDone = true;
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            //重新执行服务器轮训
       startRunPoll(wifiBdcode);

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MiddlePserson();
    }

    JsonObjectConve jsonObjectConve;
    /**
     * 开始循环监听魔盒wifi 是否连接
     */
    public void  startRunPoll(String  bdcode)
    {
      //访问服务器
        if (params==null)
        {
            params=new HashMap<String, String>();
            params.put("bdcode",bdcode);
            params.put("cust_id",((AppApplication)getApplication()).getUserId());
            jsonObjectConve=new JsonObjectConve(WifiServer.this);
        }
        jsonObjectConve.httpPost(AppApplication.HOST+"user/wifiSet",params, MacRespone.class,new JsonObjectConve.HttpCallback<MacRespone>() {
            @Override
            public void onResponse(String s, MacRespone response, int code) {
                Log.e("mDone","mDone"+mDone);
                if (!mDone)
                {
                    return;
                }
                if (response!=null)
                {

                if (response.getCode()!=1)
                {
                    //继续轮训
                    handler.sendMessageDelayed(handler.obtainMessage(), 2000);
                    return;
                }
                    //拿到MAC
                    if (response.getData()!=null)
                    {
                        //绑定成功
                        Log.e("绑定成功","绑定成功"+response.getData());
                        wifiConntent.onSuccess(response.getData().getMac());
                    }else {
                        handler.sendMessageDelayed(handler.obtainMessage(), 2000);
                    }

                }
            }
            @Override
            public void onError(String msg) {
                if (!mDone)
                {
                    return;

                }
                handler.sendMessageDelayed(handler.obtainMessage(), 2000);
            }
        });
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.e("sssss","绑定unbindService");
        mDone=false;
        super.unbindService(conn);
    }

    WifiConntent wifiConntent;
    String   wifiBdcode;
 public interface   WifiConntent
 {
     void  onSuccess(String  mac);
     void  onFail();
 }
    public class MiddlePserson  extends Binder
    {
        public void callMes(WifiConntent wifiConntentObj,String  bdcode)
        {
           wifiConntent=wifiConntentObj;
            wifiBdcode=bdcode;
            startRunPoll(bdcode);
        }
        public  void  kill()
        {
            mDone=false;
        }
    }
}
