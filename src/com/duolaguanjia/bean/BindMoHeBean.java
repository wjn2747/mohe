package com.duolaguanjia.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/23.
 */
public class BindMoHeBean implements Serializable
{
    private boolean isSelect=false;
    private String  titleName;
    private boolean  isDelete=false;
    private String  netImageUrl;
    private int  imageId;
    private String imagePath;
    private Bitmap bitmap;
    private int  id;



    public  BindMoHeBean(boolean isSelect,String  titleName,boolean  isDelete,int  imageId)
    {
        this.isSelect=isSelect;
        this.titleName=titleName;
        this.isDelete=isDelete;
        this.imageId=imageId;

    }
    public  BindMoHeBean(boolean isSelect,String  titleName,boolean  isDelete,String  imagePath)
    {
        this.isSelect=isSelect;
        this.titleName=titleName;
        this.isDelete=isDelete;
        this.imagePath=imagePath;

    }
    public  BindMoHeBean(boolean isSelect,String  titleName,boolean  isDelete)
    {
        this.isSelect=isSelect;
        this.titleName=titleName;
        this.isDelete=isDelete;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNetImageUrl() {
        return netImageUrl;
    }

    public void setNetImageUrl(String netImageUrl) {
        this.netImageUrl = netImageUrl;
    }
}
