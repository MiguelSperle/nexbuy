package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.module.coupon.application.ports.in.GetCouponsUseCase;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.GetCouponsUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.GetCouponsUseCaseOutput;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.responses.GetCouponsResponse;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final GetCouponsUseCase getCouponsUseCase;

    @GetMapping
    public ResponseEntity<Pagination<GetCouponsResponse>> getCoupons(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "percentage") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam Map<String, String> filters
    ) {
        final GetCouponsUseCaseOutput getCouponsUseCaseOutput = this.getCouponsUseCase.execute(GetCouponsUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, sort, direction, filters)
        ));

        return ResponseEntity.ok().body(GetCouponsResponse.from(getCouponsUseCaseOutput));
    }
}
