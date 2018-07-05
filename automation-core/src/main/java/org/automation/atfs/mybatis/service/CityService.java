package org.automation.atfs.mybatis.service;

import org.automation.atfs.mybatis.pojo.City;

public interface CityService {
    City selectCityById(int id);
}
