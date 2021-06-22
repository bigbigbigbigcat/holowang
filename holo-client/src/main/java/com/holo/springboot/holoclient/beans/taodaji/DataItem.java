package com.holo.springboot.holoclient.beans.taodaji;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataItem {
    private String name;
    private List<Specs> specs = new ArrayList<Specs>();
    private String storeName;
    private String monthSaleNumbers;


}
