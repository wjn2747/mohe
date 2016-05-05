package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.bean.MacBean;

/**
 * Created by Administrator on 2016/1/23.
 */
public class MacRespone extends BaseRespone
{
    private MacBean data;


    public MacBean getData() {
        return data;
    }

    public void setData(MacBean data) {
        this.data = data;
    }
}
