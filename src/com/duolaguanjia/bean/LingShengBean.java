package com.duolaguanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/6.
 */
public class LingShengBean  implements Serializable
{
    private  int icon;
    private  String txt;
    private boolean  isMianfei;
    private  int shouc;
    private  int zan;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean isMianfei() {
        return isMianfei;
    }

    public void setMianfei(boolean mianfei) {
        isMianfei = mianfei;
    }

    public int getShouc() {
        return shouc;
    }

    public void setShouc(int shouc) {
        this.shouc = shouc;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
