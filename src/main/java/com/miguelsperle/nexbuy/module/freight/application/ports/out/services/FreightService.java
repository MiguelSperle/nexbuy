package com.miguelsperle.nexbuy.module.shipping.application.ports.out.services;

import java.util.List;
import java.util.Map;

public interface FreightService {
    String consult(Map<String, Object> toMap, List<Map<String, Object>> productMapList);
}
