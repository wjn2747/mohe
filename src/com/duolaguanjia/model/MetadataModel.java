package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/25.
 */
public class MetadataModel implements Serializable
{
    private  String order_id;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
