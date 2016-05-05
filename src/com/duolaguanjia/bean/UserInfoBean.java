package com.duolaguanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/23.
 */
public class UserInfoBean implements Serializable
{
    private  String  cust_img;
    private  String cust_realname;
    private   String sex;
    private  String cust_age;
    private  String  hy;
    private  String  job;
    private  String mon;
        private  int set_pass;


    public String getCust_img() {
        return cust_img;
    }

    public void setCust_img(String cust_img) {
        this.cust_img = cust_img;
    }

    public String getCust_realname() {
        return cust_realname;
    }

    public void setCust_realname(String cust_realname) {
        this.cust_realname = cust_realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCust_age() {
        return cust_age;
    }

    public void setCust_age(String cust_age) {
        this.cust_age = cust_age;
    }

    public String getHy() {
        return hy;
    }

    public void setHy(String hy) {
        this.hy = hy;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public int getSet_pass() {
        return set_pass;
    }

    public void setSet_pass(int set_pass) {
        this.set_pass = set_pass;
    }
}
