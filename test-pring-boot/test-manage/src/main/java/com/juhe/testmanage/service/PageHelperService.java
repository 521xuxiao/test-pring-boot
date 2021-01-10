package com.juhe.testmanage.service;

import java.util.Map;

public interface PageHelperService {
    Map<String, Object> page(int currentPage, int pageSize);
}
