package com.doushi.library.model;

/**
 * 端口
 *
 * @author xiemy
 * @date 2017/10/24.
 */
public class PortData {

    public static final int PORT_80 = 0;
    public static final int PORT_81 = 1;
    public static final int PORT_82 = 2;
    public static final int PORT_83 = 3;
    public static final int PORT_84 = 4;
    public static final int PORT_85 = 5;
    public static final int PORT_86 = 6;
    public static final int PORT_87 = 7;
    public static final int PORT_88 = 8;
    public static final int CESHI = 100;
    public static final int PRE = 200;
    public static final int FORMAL = 300;

    private String portStr;
    private int port;

    public PortData(String portStr, int port) {
        this.portStr = portStr;
        this.port = port;
    }

    public String getPortStr() {
        return portStr;
    }

    public int getPort() {
        return port;
    }
}
