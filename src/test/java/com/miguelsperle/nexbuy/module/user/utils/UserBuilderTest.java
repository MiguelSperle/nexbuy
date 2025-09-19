package com.miguelsperle.nexbuy.module.user.utils;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.AuthorizationRole;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

public class UserBuilderTest {
    public static User create(UserStatus userStatus, AuthorizationRole authorizationRole, PersonType personType) {
        return User.with(
                IdentifierUtils.generateUUID(),
                "Bruce",
                "Wayne",
                "brucewayne20000@gmail.com",
                "batman123",
                "(12) 90000-1232",
                userStatus,
                authorizationRole,
                personType,
                TimeUtils.now()
        );
    }
}
