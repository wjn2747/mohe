package com.duolaguanjia.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/23.
 */
public class OrderModel implements Serializable
{
    private  String order_id;
    private  String order_at;
    private  String money;
    private String nonepay;
    private  String order_status;
    private  String word_status;
    private  String sum_nums;
    private ArrayList<ProModel> goods_detail;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_at() {
        return order_at;
    }

    public void setOrder_at(String order_at) {
        this.order_at = order_at;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getWord_status() {
        return word_status;
    }

    public void setWord_status(String word_status) {
        this.word_status = word_status;
    }

    public String getSum_nums() {
        return sum_nums;
    }

    public void setSum_nums(String sum_nums) {
        this.sum_nums = sum_nums;
    }

    public ArrayList<ProModel> getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(ArrayList<ProModel> goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getNonepay() {
        return nonepay;
    }

    public void setNonepay(String nonepay) {
        this.nonepay = nonepay;
    }
}
