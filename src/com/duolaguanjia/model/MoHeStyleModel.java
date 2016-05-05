package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/24.
 */
public class MoHeStyleModel implements Serializable
{
    private String scene_title;

    private String scene_pic;

    public String getScene_title() {
        return scene_title;
    }

    public void setScene_title(String scene_title) {
        this.scene_title = scene_title;
    }

    public String getScene_pic() {
        return scene_pic;
    }

    public void setScene_pic(String scene_pic) {
        this.scene_pic = scene_pic;
    }
}
