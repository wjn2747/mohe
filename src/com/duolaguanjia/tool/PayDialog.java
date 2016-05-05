package com.duolaguanjia.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import com.duolaguanjia.R;

/**
 * Created by Administrator on 2015/12/21.
 */
public class PayDialog
{
    Activity baseActivity;
   public  PayDialog(Activity baseActivity)
   {
       this.baseActivity=baseActivity;
      dlg = new AlertDialog.Builder(baseActivity).create();
   }
    AlertDialog dlg;
    public String  orderId;
    /**
     * 支付选择
     * @param onClickListener
     * @param baseActivity
     */
    public  void  showPayView(View.OnClickListener onClickListener)
    {
        dlg  = new AlertDialog.Builder(baseActivity).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.view_pay);
        window.findViewById(R.id.wm_yue).setOnClickListener(onClickListener);
        window.findViewById(R.id.wm_zhifubao).setOnClickListener(onClickListener);
        window.findViewById(R.id.wm_duolacz).setOnClickListener(onClickListener);
        window.findViewById(R.id.wm_bank).setOnClickListener(onClickListener);
        window.findViewById(R.id.ll_add_new).setOnClickListener(onClickListener);
    }
    public void  hideDialog()
    {
        if (dlg!=null&& dlg.isShowing())
        {
            dlg.dismiss();
            dlg=null;
        }

    }
    /**
     * 输入卡号密码
     * @param onClickListener
     * @param baseActivity
     */
    public  void  showInputCardPpwsView(View.OnClickListener onClickListener)
    {

        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.view_input_card_pws);
//        window.findViewById(R.id.wm_yue).setOnClickListener(onClickListener);
//        window.findViewById(R.id.wm_zhifubao).setOnClickListener(onClickListener);
//        window.findViewById(R.id.wm_duolacz).setOnClickListener(onClickListener);
//        window.findViewById(R.id.wm_bank).setOnClickListener(onClickListener);
//        window.findViewById(R.id.ll_add_new).setOnClickListener(onClickListener);
    }

}
