package com.duolaguanjia.tool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Administrator on 2016/1/20.
 */
public class WifiUtil
{
    Context context;
    public   int mLocalIp;
    public  WifiUtil(Context context)
    {
        this.context=context;

    }

    /**
     * 获取WIFI 密码
     * @return
     */
        public   String  getWifiName()
        {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!networkInfo.isConnected()) {
                showErrorDialog();
                return "";
            }

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            mLocalIp = info.getIpAddress();
            String ssid = info.getSSID();
            if (ssid.startsWith("\"")) {
                ssid = ssid.substring(1, ssid.length()-1);
            }
            return ssid;
        }

    void showErrorDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("请连接WIFI！");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //打开wifi 设置页面
                if(android.os.Build.VERSION.SDK_INT > 10) {
                 // 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
                    context.startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));
                } else {
                    context.startActivity(new Intent( android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }
                builder.create().dismiss();
            }
        });
        builder.create().show();
    }
}
