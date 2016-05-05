package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/25.
 */
public class CredentialModel implements Serializable
{
    private  String object;

    private   PayInfoModel   alipay;


    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public PayInfoModel getAlipay() {
        return alipay;
    }

    public void setAlipay(PayInfoModel alipay) {
        this.alipay = alipay;
    }
}
