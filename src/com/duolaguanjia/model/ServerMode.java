package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class ServerMode implements Serializable
{
    private  String service_id;
    private  String title;
    private  String id;
    private  String end_at;
    private String  clock_title;
    private String clock_pic;


    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
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
}
