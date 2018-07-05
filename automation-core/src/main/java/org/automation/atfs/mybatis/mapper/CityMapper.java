package org.automation.atfs.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.automation.atfs.mybatis.pojo.City;

@Mapper
public interface CityMapper {
    @Select("select * from city where id = #{id}")
    City selectById(@Param("id") int id);

    @Insert("insert into city(id,name,countryCode,district,population) values(#{id},#{name},#{countryCode},#{district},#{population})")
    void insertCity(@Param("id") int id,
                    @Param("name") String name,
                    @Param("countryCode") String countryCode,
                    @Param("district") String district,
                    @Param("population") int population);
}
