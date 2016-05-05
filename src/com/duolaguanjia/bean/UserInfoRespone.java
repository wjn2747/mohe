package com.duolaguanjia.bean;

/**
 * Created by Administrator on 2016/1/23.
 */
public class UserInfoRespone extends BaseRespone
{
    private  UserInfoBean  data;


    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }
}
