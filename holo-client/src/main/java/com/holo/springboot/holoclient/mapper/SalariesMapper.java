package com.holo.springboot.holoclient.mapper;

import com.holo.springboot.holoclient.pojo.Salaries;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalariesMapper {
    int deleteByPrimaryKey(@Param("empNo") Integer empNo, @Param("fromDate") Date fromDate);

    int insert(Salaries record);

    Salaries selectByPrimaryKey(@Param("empNo") Integer empNo, @Param("fromDate") Date fromDate);

    List<Salaries> selectAll();

    int updateByPrimaryKey(Salaries record);
}