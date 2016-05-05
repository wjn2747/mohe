package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class ClockModel  implements Serializable
{
    private  String clock_id;
    private  String clock_title;
    private  String clock_pic;
    private  String clock_url;
    private  String price;
    private int is_on;
    private   boolean isPlay=false;




    public String getClock_id() {
        return clock_id;
    }

    public void setClock_id(String clock_id) {
        this.clock_id = clock_id;
    }

    public String getClock_title() {
        return clock_title;
    }

    public void setClock_title(String clock_title) {
        this.clock_title = clock_title;
    }

    public String getClock_pic() {
        return clock_pic;
    }

    public void setClock_pic(String clock_pic) {
        this.clock_pic = clock_pic;
    }

    public String getClock_url() {
        return clock_url;
    }

    public void setClock_url(String clock_url) {
        this.clock_url = clock_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIs_on() {
        return is_on;
    }

    public void setIs_on(int is_on) {
        this.is_on = is_on;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
