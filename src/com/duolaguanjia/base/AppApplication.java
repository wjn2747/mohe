package com.duolaguanjia.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import com.app_sdk.exception.AppExceptionHandler;
import com.app_sdk.image_config.ImageLoaderConfig;
import com.app_sdk.tool.Util;

/**
 * Created by Night on 2015/7/21.
 */
public class AppApplication extends Application {
    public static final String SD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public static final String BASE_PATH = SD_PATH + "/medicine/";
    public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";
    public  static  String   versionName="";//版本号
    public static String deviceId;//设备号ID
    public  static   String netType;//网络类型
    public  static   String token;//网络类型
    public  AppDataInit appDataInit;
    private static Context mContext;
    public final static String    HOST="http://api.dorabox.net/";//"http://192.168.1.114/";

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate()
    {
        mContext = this;
        ImageLoaderConfig.initImageLoader(getApplicationContext(), BASE_IMAGE_CACHE);
        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler
                .getInstance(getApplicationContext()));
        versionName=Util.getAppVersionName(this);
        deviceId = Util.getImeiCode(getApplicationContext());
        netType=Util.getNetWorkType(this);
        if (appDataInit==null)
        {
            appDataInit=new AppDataInit(getApplicationContext());
        }
        super.onCreate();
    }

   public boolean  isBdMohe()
   {
       return Util.getPreferenceBoolean(this,Util.SAVE_BIND,false);
   }
    /**
     * 当前用户是否登陆
     * @return
     */
    public   boolean  isLogin()
    {
        boolean isLogin=false;
        if (Util.getPreferenceString(this, Util.SAVE_UID)!=null && !Util.getPreferenceString(this, Util.SAVE_UID).equalsIgnoreCase(""))
        {
            //用户存在
            isLogin=true;
        }
        return isLogin;

    }
    public  String  getPayPws()
    {
        return Util.getPreferenceString(this,Util.payPws);
    }
    public  void  setPayPws(String pws)
    {
        Util.putPreferenceString(this,Util.payPws,pws);
    }

    /**
     * 获取用户头像
     * @return
     */
   public  String  getUserFace()
   {
       return  Util.getPreferenceString(this, Util.SAVE_IMAGE);
   }
    /**
     * 获取到用户ID
     * @return
     */
    public  String   getUserId()
    {
        return Util.getPreferenceString(this, Util.SAVE_UID);
    }

    /**
     * 获取到用户手机号
     * @return
     */
    public  String  getUserMoble()
    {
        return Util.getPreferenceString(this, Util.SAVE_PHONE);
    }

    /**
     * 获取到用户昵称
     * @return
     */
    public  String  getRongYunToken()
    {
        return Util.getPreferenceString(this, Util.SAVE_TOKEN);
    }

    /**
     * 拿到TOKEND
     * @return
     */
    public  String  getUserRealname()
    {
        return Util.getPreferenceString(this, Util.SAVE_NICK_NAME);
    }


    /**
     * 退出登陆
     */
    public void outLogin()
    {
        setPayPws("");
        Util.putPreferenceBoolean(this, Util.SAVE_BIND, false);
        Util.putPreferenceString(this, Util.SAVE_NICK_NAME, "");
        Util.putPreferenceString(this, Util.SAVE_PHONE, "");
        Util.putPreferenceString(this, Util.SAVE_UID, "");
        Util.putPreferenceString(this, Util.SAVE_TOKEN, "");
        Util.putPreferenceString(this, Util.SAVE_KEFU_KEFU, "");
    }

}
