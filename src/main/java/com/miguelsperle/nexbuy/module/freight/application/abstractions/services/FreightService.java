package com.miguelsperle.nexbuy.module.freight.application.abstractions.services;

import java.util.List;
import java.util.Map;

public interface FreightService {
    String consult(Map<String, Object> toMap, List<Map<String, Object>> itemMapList);
}
