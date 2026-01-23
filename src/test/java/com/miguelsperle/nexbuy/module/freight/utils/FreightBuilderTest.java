package com.miguelsperle.nexbuy.module.freight.utils;

import com.miguelsperle.nexbuy.module.freight.domain.entities.Freight;
import com.miguelsperle.nexbuy.shared.domain.utils.DecimalUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class FreightBuilderTest {
    public static Freight create() {
        return Freight.with(
                IdentifierUtils.generateUUID(),
                IdentifierUtils.generateUUID(),
                "test-freight-name",
                "test-company-name",
                DecimalUtils.valueOf(20),
                3,
                1,
                3,
                TimeUtils.now()
        );
    }
}
