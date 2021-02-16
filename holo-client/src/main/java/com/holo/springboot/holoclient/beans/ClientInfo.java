package com.holo.springboot.holoclient.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("${clientInfo}")
public class ClientInfo {

    @Value("${clientInfo.id_kind}")
    private String id_kind;//证件类型
    private String id_no;  //证件号
    private String client_name;
    @Value("${clientInfo.branch_no}")
    private String branch_no;
    @Value("${clientInfo.remark}")
    private String remark;

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

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "id_kind='" + id_kind + '\'' +
                ", id_no='" + id_no + '\'' +
                ", client_name='" + client_name + '\'' +
                ", branch_no='" + branch_no + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
