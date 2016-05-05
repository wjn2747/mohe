package com.duolaguanjia.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ProBean  implements Serializable
{
    private  String biz_title;
    private  String biz_id;
    private String biz_intro;
    private String biz_bg;
    private  String biz_spec;
    private  String question;

    private  String biz_logo;
    private  String biz_kz;




    public String getBiz_title() {
        return biz_title;
    }

    public void setBiz_title(String biz_title) {
        this.biz_title = biz_title;
    }

    public String getBiz_id() {
        return biz_id;
    }

    public void setBiz_id(String biz_id) {
        this.biz_id = biz_id;
    }

    public String getBiz_logo() {
        return biz_logo;
    }

    public void setBiz_logo(String biz_logo) {
        this.biz_logo = biz_logo;
    }

    public String getBiz_kz() {
        return biz_kz;
    }

    public void setBiz_kz(String biz_kz) {
        this.biz_kz = biz_kz;
    }

    public String getBiz_intro() {
        return biz_intro;
    }

    public void setBiz_intro(String biz_intro) {
        this.biz_intro = biz_intro;
    }

    public String getBiz_bg() {
        return biz_bg;
    }

    public void setBiz_bg(String biz_bg) {
        this.biz_bg = biz_bg;
    }

    public String getBiz_spec() {
        return biz_spec;
    }

    public void setBiz_spec(String biz_spec) {
        this.biz_spec = biz_spec;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
