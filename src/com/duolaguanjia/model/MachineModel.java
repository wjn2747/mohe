package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class MachineModel implements Serializable
{
    private  String macpic;
    private  String macname;
    private  String month;
    private  String zlp_temp;
    private  String kq_temp;
    private  String scene_title;
    private  String scene_id;
    private  String qishu;
    private  String mac_status;


    public String getMacpic() {
        return macpic;
    }

    public void setMacpic(String macpic) {
        this.macpic = macpic;
    }

    public String getMacname() {
        return macname;
    }

    public void setMacname(String macname) {
        this.macname = macname;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getZlp_temp() {
        return zlp_temp;
    }

    public void setZlp_temp(String zlp_temp) {
        this.zlp_temp = zlp_temp;
    }

    public String getKq_temp() {
        return kq_temp;
    }

    public void setKq_temp(String kq_temp) {
        this.kq_temp = kq_temp;
    }

    public String getScene_title() {
        return scene_title;
    }

    public void setScene_title(String scene_title) {
        this.scene_title = scene_title;
    }

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public String getMac_status() {
        return mac_status;
    }

    public void setMac_status(String mac_status) {
        this.mac_status = mac_status;
    }
}
