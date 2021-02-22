package com.holo.springboot.holoclient.mapper;

import com.holo.springboot.holoclient.pojo.DeptEmp;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeptEmpMapper {
    int deleteByPrimaryKey(@Param("empNo") Integer empNo, @Param("deptNo") String deptNo);

    int insert(DeptEmp record);

    DeptEmp selectByPrimaryKey(@Param("empNo") Integer empNo, @Param("deptNo") String deptNo);

    List<DeptEmp> selectAll();

    int updateByPrimaryKey(DeptEmp record);
}