package com.miguelsperle.nexbuy.module.user.utils.mocks;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;

public class UserMock {
    public static User create() {
        return User.newUser(
                "Test First Name",
                "Test Last Name",
                "testUser@gmail.com",
                "testUser123",
                "(00) 00000-0000",
                PersonType.NATURAL_PERSON
        );
    }
}
