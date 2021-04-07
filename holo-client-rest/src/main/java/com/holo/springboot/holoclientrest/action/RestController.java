package com.holo.springboot.holoclientrest.action;

import com.holo.springboot.holoclientrest.entity.OrderInfo;
import com.holo.springboot.holoclientrest.entity.Result;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/Rest")
public class RestController {

    private final RestTemplate restTemplate;

    public RestController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @RequestMapping("/qryOrderInfo/{name}")
    public Result qryOrderInfo(@PathVariable String name){
        Result forObject = restTemplate.getForObject("http://127.0.0.1:8088/SpringBoot/qryOrderInfo/{name}", Result.class, name);

       return forObject;
    }

    @RequestMapping("/addOrderInfo/{orderInfo}")
    public void addOrderInfo(@PathVariable OrderInfo orderInfo){
        ResponseEntity<Result> resultResponseEntity = restTemplate.postForEntity("http://127.0.0.1:8088/SpringBoot/addOrderInfo", orderInfo, Result.class);
        System.out.println(resultResponseEntity.toString());
    }

    @RequestMapping("/addOrderInfoForJson")
    @ResponseBody
    public void addOrderInfoForJson (OrderInfo orderInfo){
        ResponseEntity<Result> resultResponseEntity = restTemplate.postForEntity("http://127.0.0.1:8088/SpringBoot/addOrderInfoForJson", orderInfo, Result.class);
        System.out.println(resultResponseEntity.toString());
    }
}
