package com.holo.springboot.holoclient.beans;


public class OsvBaseForm {

    // baseMap基础参数
    private String user_token;
    private String op_entrust_way;
    private String op_station;

    private String operator_no;
    private String op_branch_no;
    private String user_type;
    private String acpt_channel;
    private String op_password;
    private String branch_no;

    public String getOperator_no() {
        return operator_no;
    }

    public void setOperator_no(String operator_no) {
        this.operator_no = operator_no;
    }

    public String getOp_branch_no() {
        return op_branch_no;
    }

    public void setOp_branch_no(String op_branch_no) {
        this.op_branch_no = op_branch_no;
    }

    public String getOp_station() {
        return op_station;
    }

    public void setOp_station(String op_station) {
        this.op_station = op_station;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getOp_entrust_way() {
        return op_entrust_way;
    }

    public void setOp_entrust_way(String op_entrust_way) {
        this.op_entrust_way = op_entrust_way;
    }

    public String getOp_password() {
        return op_password;
    }

    public void setOp_password(String op_password) {
        this.op_password = op_password;
    }

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getAcpt_channel() {
        return acpt_channel;
    }

    public void setAcpt_channel(String acpt_channel) {
        this.acpt_channel = acpt_channel;
    }

    @Override
    public String toString() {
        return "OsvBaseForm [operator_no=" + operator_no + ", op_branch_no=" + op_branch_no + ", op_station="
                + op_station + ", user_type=" + user_type + ", op_entrust_way=" + op_entrust_way + ", op_password="
                + op_password + ", branch_no=" + branch_no + "]";
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
