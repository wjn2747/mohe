package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class BillModel implements Serializable
{
    private  String finance_money;
    private  String money_remark;
    private  String month;
    private  String add_at;
    private  boolean  isShowMon;




    public String getFinance_money() {
        return finance_money;
    }

    public void setFinance_money(String finance_money) {
        this.finance_money = finance_money;
    }

    public String getMoney_remark() {
        return money_remark;
    }

    public void setMoney_remark(String money_remark) {
        this.money_remark = money_remark;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAdd_at() {
        return add_at;
    }

    public void setAdd_at(String add_at) {
        this.add_at = add_at;
    }

    public boolean isShowMon() {
        return isShowMon;
    }

    public void setShowMon(boolean showMon) {
        isShowMon = showMon;
    }
}
