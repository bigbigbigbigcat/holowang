package com.holo.springboot.netty.nettyTest.encode;

public class Client {

    private String client_name;//证件类型
    private String id_kind;//证件类型
    private String id_no;  //证件号

    public String getId_kind() {
        return id_kind;
    }

    public void setId_kind(String id_kind) {
        this.id_kind = id_kind;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getClient_name() {
        return client_name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_name='" + client_name + '\'' +
                ", id_kind='" + id_kind + '\'' +
                ", id_no='" + id_no + '\'' +
                '}';
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
