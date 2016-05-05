package com.duolaguanjia.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.ToastUtil;
import com.app_sdk.view.WidgetHttpLoadView;
import com.duolaguanjia.R;
import com.duolaguanjia.tool.SystemBarTintManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Night on 2015/7/21.
 */
public abstract class BaseActivity extends FragmentActivity
{
    protected WidgetHttpLoadView httpView;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    private ToastUtil toast=new ToastUtil(this);
    public   JsonObjectConve jsonObjectConve;
    public  AppApplication  application;
    public Map<String, String> params;
    private AlertDialog alertDialog;
    public ProgressDialog dialog;




    private  Toast myToastOk;
    public static void initSystemBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);
            //改变标题栏的高度

        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setStatusBarTintResource(R.color.app_color);
    }
    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }
    public void  showToastOK()
    {
        if (myToastOk == null) {
            myToastOk =new Toast(this);  ;
            View layout = LayoutInflater.from(this).inflate(R.layout.toast_view,null);
            // 设置toast显示的位置
            myToastOk.setGravity(Gravity.CENTER, 0, 0);
            myToastOk.setDuration(Toast.LENGTH_SHORT);
            myToastOk.setView(layout);
        }
        // 设置布局
        myToastOk.show();
    }
 public  void  hideKey()
 {
     if (getCurrentFocus()!=null)
     {
         ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                 .hideSoftInputFromWindow(getCurrentFocus()
                                 .getWindowToken(),
                         InputMethodManager.HIDE_NOT_ALWAYS);
     }
 }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // getWindow().setFormat(PixelFormat.RGB_565);
        application=(AppApplication) getApplication();
        jsonObjectConve=new JsonObjectConve(this);
        params=new HashMap<>();
        initView(savedInstanceState);
        //initSystemBar(this);
    }
    public  void  showDiaog()
    {
        if (alertDialog==null)
        {
            alertDialog= new AlertDialog.Builder(this).create();
        }
        if ( !alertDialog.isShowing())
        {
            alertDialog.show();
            Window window = alertDialog.getWindow();
            // *** 主要就是在这里实现这种效果的.
            // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
            window.setContentView(R.layout.dialog_load_view);
        }
    }


 public  abstract  void  initView(Bundle savedInstanceState);

    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }


    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    public void openActivity(String pAction) {
        openActivity(pAction, null);
    }



    public void openProcessActivity(Class<?> pClass, Bundle pBundle)
     {
   //       openActivity(pClass,pBundle);
        Intent intentWeb = new Intent(pClass.getName());
        intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (pBundle != null) {
            intentWeb.putExtras(pBundle);
        }
        startActivity(intentWeb);
    }


    public void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    public void showTxtDialog(String message)
    {
        if (dialog==null )

        {
            dialog = new ProgressDialog(this);
            dialog.setCanceledOnTouchOutside(false);
        }

        if ( !dialog.isShowing())
        {
            dialog.setMessage(message);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

    }
    public void hideTxtDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
    @Override
    protected void onDestroy() {
        if(alertDialog != null){
            alertDialog.dismiss();
            alertDialog=null;
        }
        if(dialog != null){
            dialog.dismiss();
            dialog=null;
        }
        super.onDestroy();
    }
    public void hideDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
    @Override
    protected void onStop()
    {
        hideDialog();
        super.onStop();
    }



    public abstract   void httpError(String code,String response);





    public void DisPlay(String content) {
        toast.shortToast(content);
    }
}
