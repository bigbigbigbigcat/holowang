package com.holo.springboot.holoclient.mapper;

import com.holo.springboot.holoclient.pojo.Employees;
import java.util.List;

public interface EmployeesMapper {
    int deleteByPrimaryKey(Integer empNo);

    int insert(Employees record);

    Employees selectByPrimaryKey(Integer empNo);

    List<Employees> selectAll();

    int updateByPrimaryKey(Employees record);
}