package com.holo.springboot.holoclient.beans.taodaji;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {
    private List<DataItem> items = new ArrayList<DataItem>();

    public List<DataItem> getItems() {
        return items;
    }

    public void setItems(List<DataItem> items) {
        this.items = items;
    }
}
