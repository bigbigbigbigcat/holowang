package com.holo.springboot.holoclient.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//@PropertySource("${clientInfo}")
@ConfigurationProperties(prefix = "order")
public class OrderInfo {

    private String order_id;
    @JsonProperty("oname")//通过这种方式来设置别名
    private String order_name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String order_type;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",locale = "zh")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "order_id='" + order_id + '\'' +
                ", order_name='" + order_name + '\'' +
                ", order_type='" + order_type + '\'' +
                ", date=" + date +
                '}';
    }
}
