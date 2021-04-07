package com.holo.springboot.holoclient.action;

import com.holo.springboot.holoclient.beans.ClientInfo;
import com.holo.springboot.holoclient.beans.OrderInfo;
import com.holo.springboot.holoclient.beans.Result;
import com.holo.springboot.holoclient.mapper.DepartmentsMapper;
import com.holo.springboot.holoclient.mapper.EmployeesMapper;
import com.holo.springboot.holoclient.pojo.Departments;
import com.holo.springboot.holoclient.pojo.Employees;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "订单管理相关接口")
public class OrderController {
    private final RestTemplate restTemplate;

    public OrderController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Autowired
    private ClientInfo clientInfo;

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private DepartmentsMapper departmentsMapper;


    @RequestMapping(value = "/qryOrderInfo/{name}",method = RequestMethod.GET)
    @ApiOperation("根据id查询用户的接口")
    @ApiImplicitParam(name = "name", value = "用户名", defaultValue = " ", required = true)
    public Result qryOrderInfo(@PathVariable String name) {
//        restTemplate.getForObject()
//        return this.restTemplate.getForObject("/{name}/details", Result.class, name);
        clientInfo.setClient_name(name);
        return new Result(200,"成功",clientInfo);
    }

    @RequestMapping(value = "/addOrderInfo/{name}",method = RequestMethod.POST)
    @CrossOrigin//支持跨域
    public Result addOrderInfo(@PathVariable String name) {
//        restTemplate.getForObject()
//        return this.restTemplate.getForObject("/{name}/details", Result.class, name);
        clientInfo.setClient_name(name);
        return new Result(200,"成功",clientInfo);
    }

    @RequestMapping(value = "/addOrderInfoForJson",method = RequestMethod.POST)
    @ResponseBody
    public Result addOrderInfoForJson(OrderInfo orderInfo) {
        int i = 1/0;

//        restTemplate.getForObject()
//        return this.restTemplate.getForObject("/{name}/details", Result.class, name);
        orderInfo.setDate(new Date());
        return new Result(200,"成功",orderInfo);
    }

    @RequestMapping(value = "/employees",method = RequestMethod.GET)
    @ResponseBody
    public List<Employees> getEmployees(OrderInfo orderInfo) {

//        restTemplate.getForObject()
//        return this.restTemplate.getForObject("/{name}/details", Result.class, name);
//        orderInfo.setDate(new Date());
//        return new Result(200,"成功",orderInfo);
        List<Employees> employees = employeesMapper.selectAll();
        return employees;
    }


    @RequestMapping(value = "/departmentsMapper",method = RequestMethod.GET)
    @ApiOperation("部门信息查询")
    @ResponseBody
    public List<Departments> getDepartments(OrderInfo orderInfo) {

//        restTemplate.getForObject()
//        return this.restTemplate.getForObject("/{name}/details", Result.class, name);
//        orderInfo.setDate(new Date());
//        return new Result(200,"成功",orderInfo);
        List<Departments> departments = departmentsMapper.selectAll();
        return departments;
    }
}
