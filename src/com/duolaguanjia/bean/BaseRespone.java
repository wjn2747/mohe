package com.duolaguanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/19.
 */
public class BaseRespone implements Serializable
{
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
