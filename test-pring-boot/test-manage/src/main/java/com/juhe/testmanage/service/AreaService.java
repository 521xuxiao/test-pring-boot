package com.juhe.testmanage.service;

import java.util.List;
import java.util.Map;

public interface AreaService {
    void addArea(Map<String, Object> params);

    void updateArea(Map<String, Object> params);

    void deleteArea(String id);

    List<Map<String, Object>> queryAreatList();
}
