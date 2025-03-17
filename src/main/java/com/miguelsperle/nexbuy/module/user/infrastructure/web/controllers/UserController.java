package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IMediator;
import com.miguelsperle.nexbuy.core.infrastructure.web.baseController.AbstractBaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends AbstractBaseController {
    private final IMediator mediator;
}
