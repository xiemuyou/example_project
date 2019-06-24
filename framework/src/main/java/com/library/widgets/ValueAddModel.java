package com.library.widgets;

/**
 * @author zhangw
 * @date 2017/9/5.
 * email: zhangwei@kingnet.com
 * 增值提示
 */
public class ValueAddModel {

    private int intimacy;
    private int growvalue;
    private int glod;

    public int getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(int intimacy) {
        this.intimacy = intimacy;
    }

    public int getGrowvalue() {
        return growvalue;
    }

    public void setGrowvalue(int growvalue) {
        this.growvalue = growvalue;
    }

    public int getGlod() {
        return glod;
    }

    public void setGlod(int glod) {
        this.glod = glod;
    }

    public boolean hasValue() {
        return intimacy != 0 || growvalue != 0 || glod != 0;
    }
}
