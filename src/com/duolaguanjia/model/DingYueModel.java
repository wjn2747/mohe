package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class DingYueModel implements Serializable
{
    private  String service_id;

    private  String price;
    private  int isDy;


    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIsDy() {
        return isDy;
    }

    public void setIsDy(int isDy) {
        this.isDy = isDy;
    }
}
