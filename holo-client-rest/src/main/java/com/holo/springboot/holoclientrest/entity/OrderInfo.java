package com.holo.springboot.holoclientrest.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("${clientInfo}")
@ConfigurationProperties(prefix = "order")
public class OrderInfo {

    private String order_id;
    private String order_name;
    private String order_type;

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
                '}';
    }
}
