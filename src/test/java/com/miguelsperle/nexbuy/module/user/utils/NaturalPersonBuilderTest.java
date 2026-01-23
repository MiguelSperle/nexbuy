package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.NaturalPerson;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class NaturalPersonBuilderTest {
    public static NaturalPerson create(String userId) {
        return NaturalPerson.with(
                IdentifierUtils.generateUUID(),
                userId,
                "80545250013",
                "508358772",
                TimeUtils.now()
        );
    }
}
