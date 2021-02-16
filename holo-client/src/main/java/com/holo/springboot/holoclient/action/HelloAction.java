package com.holo.springboot.holoclient.action;

import com.holo.springboot.holoclient.Field;
import com.holo.springboot.holoclient.beans.ClientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @RestController 的意思就是 Controller 里面的方法都以 json 格式输出，不用再写什么 jackjson 配置的了！
 */
@RestController //RestController= Response+Controller
//@Controller
//@RequestMapping("/hello")
public class HelloAction {

    @Autowired
    private ClientInfo clientInfo;

    @RequestMapping(value = "/Holo/sayHi")
    public String sayHi() {
        return "sayHi~";
    }

    @RequestMapping(value = "/Holo/sayHello")
    public String sayHello() {
        return "sayHello~";
    }

    @RequestMapping(value = "/Holo/sayMap")
    public Map<String, String> sayMap() {
        Map<String, String> stringMap = new HashMap<>();

        stringMap.put(Field.CLIENT_ACCOUNT, "00200934");
        stringMap.put(Field.CLIENT_NAME, "王骥威");

        return stringMap;
    }

    @RequestMapping(value = "/Holo/sayList")
    public List<Map<String, String>> sayList() {
        List<Map<String, String>> returnList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, String> stringMap = new HashMap<>();

            stringMap.put(Field.CLIENT_ACCOUNT, "00200934" + i);
            stringMap.put(Field.CLIENT_NAME, "王骥威" + i);

            returnList.add(stringMap);
        }
        Map<String, String> stringMap = new HashMap<>();

        stringMap.put(Field.CLIENT_ACCOUNT, "911---1199");
        stringMap.put(Field.CLIENT_NAME, clientInfo.toString());

        returnList.add(stringMap);
        return returnList;
    }
}
