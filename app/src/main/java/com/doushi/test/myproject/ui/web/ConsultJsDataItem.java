package com.doushi.test.myproject.ui.web;

import java.util.List;

public class ConsultJsDataItem {

    /**
     * cmd : 300
     * width : 981
     * height : 512
     * top : 146
     * left : 0
     * url : http://dingyue.nosdn.127.net/jaPRqrxnBQCLVwuCUYJQrI2N7TlvYKYqVZcqmVV9WpTHZ1529466963251compressflag.jpg
     * index : 0
     * data : [{"top":146,"left":0},{"top":823,"left":0},{"top":1698,"left":0}]
     */

    private int cmd;
    private int width;
    private int height;
    private int top;
    private int left;
    private String url;
    private int index;
    private List<DataBean> data;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * top : 146
         * left : 0
         */

        private float top;
        private float left;
        private float width;
        private float height;

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public float getTop() {
            return top;
        }

        public void setTop(float top) {
            this.top = top;
        }

        public float getLeft() {
            return left;
        }

        public void setLeft(float left) {
            this.left = left;
        }
    }
}
