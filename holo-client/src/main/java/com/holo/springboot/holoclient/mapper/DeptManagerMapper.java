package com.holo.springboot.holoclient.mapper;

import com.holo.springboot.holoclient.pojo.DeptManager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DeptManagerMapper {
    int deleteByPrimaryKey(@Param("empNo") Integer empNo, @Param("deptNo") String deptNo);

    int insert(DeptManager record);

    DeptManager selectByPrimaryKey(@Param("empNo") Integer empNo, @Param("deptNo") String deptNo);

    List<DeptManager> selectAll();

    int updateByPrimaryKey(DeptManager record);
}