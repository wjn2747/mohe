package com.duolaguanjia.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Night on 2015/7/21.
 */
public abstract class BaseFragment extends Fragment
{
    public  BaseActivity baseActivity;

//    public android.os.Handler httpHander = new android.os.Handler() {
//        public void handleMessage(android.os.Message msg) {
//            baseActivity.hideDialog();
//            switch (msg.what) {
//                case -1:
//                    break;
//                case 1:
//                    BaseResponse data = (BaseResponse) msg.obj;
//                    if (data == null) {
//                        HttpHandler.throwError(
//                                baseActivity,
//                                new CustomHttpException(
//                                        HttpUtil.HttpCode_ServiceError, "服务器忙"));
//                        httpError(HttpUtil.HttpCode_ServiceError,data);
//                        return;
//                    }
//                    if (data.getCode().equalsIgnoreCase(HttpUtil.ERROR_CODE_TOKEN_TIMEOUT+"")) {
//                        //toast.shortToast(data.getMsg());
//                        httpError(HttpUtil.ERROR_CODE_TOKEN_TIMEOUT,data);
//                        return;
//                    }
//                    if (data.getCode().equalsIgnoreCase("0"))
//                    {
//                        httpError(-1,data);
//                        return;
//                    }
//
//                    httpOk(data);
//                    break;
//            }
//
//        };
//    };
    public BaseFragment()
    {

    }
     @Override
    public void onAttach(Activity activity) {
        this.baseActivity= (BaseActivity) activity;
        super.onAttach(activity);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return getView(inflater,container,savedInstanceState);
    }

    public abstract   View  getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);



}
