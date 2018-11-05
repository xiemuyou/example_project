package com.doushi.test.myproject.model.user;

import java.io.Serializable;

/**
 * 用户数据
 *
 * @author xiemy
 * @date 2018/2/28.
 */
public class UserInfo implements Serializable {

    /**
     * 用户id 自增id,
     */
    private int uid;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 注册手机号
     */
    private String phone;
    /**
     * 用户token
     */
    private String token;
    /**
     * 用户头像
     */
    private String head;
    /**
     * 注册用户密码
     */
    private String pwd_hash;
    /**
     * 注册类型
     * 1:游客
     * 2:手机注册
     * 3:QQ号注册
     * 4:微信注册
     * 5:微博注册
     */
    private int regtype;
    /**
     * 用户成长值
     */
    private int grow_value;
    /**
     * 用户等级
     */
    private int user_level;
    /**
     * 用户签名
     */
    private String sign;
    /**
     * 用户位置
     */
    private String location;
    /**
     * 用户性别	0：未知 1：男 2：女
     */
    private int gender;
    /**
     * 是否是师父	0：普通用户 1：师父
     */
    private int master_flag;
    /**
     * 用户点赞数
     */
    private int like_count;
    /**
     * 删除状态	tinyint
     * -1： 删除表示用户已经不存在，会返回 ERR_NO_USER；
     * 1:  冻结状态 表示用户存在，但不能用app任何功能，需要运营解冻才能使用app；
     * -2： 禁言
     * -3：禁发视频
     * 0：正常，功能没有限制
     */
    private int state;
    /**
     * 删除时间	timestamp	state为-1时，配合使用
     */
    private String del_time;

    /**
     * 关注用户人数
     */
    private int follow_count;
    /**
     * 银元数
     */
    private int silver;
    /**
     * 余额
     */
    private double balance;

    /**
     * 拜师券
     */
    private int ex_ticket;
    /**
     * 积分
     */
    private int gold;
    /**
     * 头衔
     */
    private String title_url;
    /**
     * 简介
     */
    private String introduction;
    /**
     * 师父擅长
     */
    private String skill;

    /**
     * 是否完成新手任务
     * 0：未完成
     * 1：已完成
     */
    private int finish_new_task;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPwd_hash() {
        return pwd_hash;
    }

    public void setPwd_hash(String pwd_hash) {
        this.pwd_hash = pwd_hash;
    }

    public int getRegtype() {
        return regtype;
    }

    public void setRegtype(int regtype) {
        this.regtype = regtype;
    }

    public int getGrow_value() {
        return grow_value;
    }

    public void setGrow_value(int grow_value) {
        this.grow_value = grow_value;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMaster_flag() {
        return master_flag;
    }

    public void setMaster_flag(int master_flag) {
        this.master_flag = master_flag;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDel_time() {
        return del_time;
    }

    public void setDel_time(String del_time) {
        this.del_time = del_time;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getEx_ticket() {
        return ex_ticket;
    }

    public void setEx_ticket(int ex_ticket) {
        this.ex_ticket = ex_ticket;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getTitle() {
        return title_url;
    }

    public void setTitle_url(String title_url) {
        this.title_url = title_url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getFinish_new_task() {
        return finish_new_task;
    }

    public void setFinish_new_task(int finish_new_task) {
        this.finish_new_task = finish_new_task;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getTitle_url() {
        return title_url;
    }
}
