package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.CreateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.CreateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.requests.CreateCouponRequest;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/coupons")
@RequiredArgsConstructor
public class CouponAdminController {
    private final CreateCouponUseCase createCouponUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> createCoupon(@RequestBody @Valid CreateCouponRequest createCouponRequest) {
        this.createCouponUseCase.execute(CreateCouponUseCaseInput.with(
                createCouponRequest.code(),
                createCouponRequest.percentage(),
                createCouponRequest.minimumPurchaseAmount(),
                createCouponRequest.couponType(),
                createCouponRequest.usageLimit(),
                createCouponRequest.isActive(),
                createCouponRequest.expiresIn()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Coupon created successfully"));
    }
}
