package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.DingYueModel;

/**
 * Created by Administrator on 2016/1/29.
 */
public class DingYueRespone extends BaseRespone
{
    private DingYueModel data;


    public DingYueModel getData() {
        return data;
    }

    public void setData(DingYueModel data) {
        this.data = data;
    }
}
