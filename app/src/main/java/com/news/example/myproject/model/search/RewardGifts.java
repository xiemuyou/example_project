package com.news.example.myproject.model.search;

import java.io.Serializable;

/**
 * 打赏礼物
 * Created by xiemy on 2017/5/16.
 */
public class RewardGifts implements Serializable {

    private int giftid; //礼物ID
    private String goodsDes; //礼物描述
    private int goodsid; //礼物id
    private String img; //礼物图片
    private String name; //礼物名称
    private int price; //礼物价格
    private String time; //打赏时间
    private boolean choice; //选择

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public int getGiftid() {
        return giftid;
    }

    public String getGoodsDes() {
        return goodsDes;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}