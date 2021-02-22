package com.holo.springboot.holoclient.mapper;

import com.holo.springboot.holoclient.pojo.Titles;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TitlesMapper {
    int deleteByPrimaryKey(@Param("empNo") Integer empNo, @Param("title") String title, @Param("fromDate") Date fromDate);

    int insert(Titles record);

    Titles selectByPrimaryKey(@Param("empNo") Integer empNo, @Param("title") String title, @Param("fromDate") Date fromDate);

    List<Titles> selectAll();

    int updateByPrimaryKey(Titles record);
}