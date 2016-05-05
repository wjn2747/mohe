package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ModelBean implements Serializable
{
    private String  cust_id;
    private  String set_pass;
    private   String is_bd;






    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getSet_pass() {
        return set_pass;
    }

    public void setSet_pass(String set_pass) {
        this.set_pass = set_pass;
    }

    public String getIs_bd() {
        return is_bd;
    }

    public void setIs_bd(String is_bd) {
        this.is_bd = is_bd;
    }
}
