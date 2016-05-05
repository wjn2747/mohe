package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.RefundModel;

/**
 * Created by Administrator on 2016/1/28.
 */
public class BackRespone extends BaseRespone
{
    private RefundModel data;

    public RefundModel getData() {
        return data;
    }

    public void setData(RefundModel data) {
        this.data = data;
    }
}
