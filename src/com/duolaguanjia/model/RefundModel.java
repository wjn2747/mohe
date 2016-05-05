package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/28.
 */
public class RefundModel implements Serializable
{
    private  String refund_type;
    private  String refund_mon;
    private String refund_content;
    private  String order_status;

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public String getRefund_mon() {
        return refund_mon;
    }

    public void setRefund_mon(String refund_mon) {
        this.refund_mon = refund_mon;
    }

    public String getRefund_content() {
        return refund_content;
    }

    public void setRefund_content(String refund_content) {
        this.refund_content = refund_content;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
