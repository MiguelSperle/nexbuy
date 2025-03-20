package com.miguelsperle.nexbuy.module.user.application.dtos;

import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserUseCaseOutput {
    private User user;
}
