package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.LegalPerson;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class LegalPersonBuilderTest {
    public static LegalPerson create(String userId) {
        return LegalPerson.with(
                IdentifierUtils.generateUUID(),
                userId,
                "32378818000139",
                "Test Company LTDA",
                "Test Company",
                "78105933",
                TimeUtils.now()
        );
    }
}
