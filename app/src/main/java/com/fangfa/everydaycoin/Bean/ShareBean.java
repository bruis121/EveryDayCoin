package com.fangfa.everydaycoin.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class ShareBean implements Serializable{
    private  String  title;
    private  String  url;
    private  String  desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }







    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
