package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.controllers.admin;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.ActivateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.in.CreateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.in.DeactivateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.ports.in.UpdateCouponUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.ActivateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.CreateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.DeactivateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.UpdateCouponUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.requests.CreateCouponRequest;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.requests.UpdateCouponRequest;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.in.web.dtos.responses.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/coupons")
@RequiredArgsConstructor
public class CouponAdminController {
    private final CreateCouponUseCase createCouponUseCase;
    private final UpdateCouponUseCase updateCouponUseCase;
    private final ActivateCouponUseCase activateCouponUseCase;
    private final DeactivateCouponUseCase deactivateCouponUseCase;

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

    @PatchMapping("/{couponId}")
    public ResponseEntity<MessageResponse> updateCoupon(
            @PathVariable String couponId,
            @RequestBody @Valid UpdateCouponRequest updateCouponRequest
    ) {
        this.updateCouponUseCase.execute(UpdateCouponUseCaseInput.with(
                couponId,
                updateCouponRequest.code(),
                updateCouponRequest.percentage(),
                updateCouponRequest.minimumPurchaseAmount(),
                updateCouponRequest.expiresIn()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Coupon updated successfully"));
    }

    @PatchMapping("/{couponId}/activate")
    public ResponseEntity<MessageResponse> activateCoupon(@PathVariable String couponId) {
        this.activateCouponUseCase.execute(ActivateCouponUseCaseInput.with(couponId));

        return ResponseEntity.ok().body(MessageResponse.from("Coupon successfully activated"));
    }

    @PatchMapping("/{couponId}/deactivate")
    public ResponseEntity<MessageResponse> deactivateCoupon(@PathVariable String couponId) {
        this.deactivateCouponUseCase.execute(DeactivateCouponUseCaseInput.with(couponId));

        return ResponseEntity.ok().body(MessageResponse.from("Coupon successfully deactivated"));
    }
}
