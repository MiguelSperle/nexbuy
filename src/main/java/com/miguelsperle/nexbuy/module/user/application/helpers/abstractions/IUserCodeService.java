package com.miguelsperle.nexbuy.module.user.application.helpers.abstractions;

import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;

public interface IUserCodeService {
    UserCode getUserCodeByCodeAndCodeType(String code, String codeType);
    void validateUserCodeExpiration(UserCode userCode);
}
