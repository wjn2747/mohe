package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.ProBean;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ProDescriptRespone  extends BaseRespone
{
    private ProBean data;


    public ProBean getData() {
        return data;
    }

    public void setData(ProBean data) {
        this.data = data;
    }
}
