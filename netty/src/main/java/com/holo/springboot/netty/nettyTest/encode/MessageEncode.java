package com.holo.springboot.netty.nettyTest.encode;

/**
 * 自定义协议包
 */
public class MessageEncode {
    private int length;
    private byte[] context;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContext() {
        return context;
    }

    public void setContext(byte[] context) {
        this.context = context;
    }
}
