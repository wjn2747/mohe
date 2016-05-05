package com.duolaguanjia.bean;

import com.duolaguanjia.model.ModelBean;

/**
 * Created by Administrator on 2016/1/19.
 */
public class LoginRespone extends BaseRespone
{
    private ModelBean data;

    public ModelBean getData() {
        return data;
    }

    public void setData(ModelBean data) {
        this.data = data;
    }
}
