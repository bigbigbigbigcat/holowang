package com.holo.springboot.holoclient.mapper;

import com.holo.springboot.holoclient.pojo.Departments;
import java.util.List;

public interface DepartmentsMapper {
    int deleteByPrimaryKey(String deptNo);

    int insert(Departments record);

    Departments selectByPrimaryKey(String deptNo);

    List<Departments> selectAll();

    int updateByPrimaryKey(Departments record);
}