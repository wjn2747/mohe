package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/23.
 */
public class ProModel implements Serializable
{
    private  String goods_pic;
    private  String goods_title;
    private  String pay_price;
    private  String pay_nums;


    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getPay_price() {
        return pay_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    public String getPay_nums() {
        return pay_nums;
    }

    public void setPay_nums(String pay_nums) {
        this.pay_nums = pay_nums;
    }
}
