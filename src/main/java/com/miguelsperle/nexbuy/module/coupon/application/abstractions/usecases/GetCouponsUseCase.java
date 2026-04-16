package com.miguelsperle.nexbuy.module.coupon.application.abstractions.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.GetCouponsUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.GetCouponsUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;

public interface GetCouponsUseCase extends UseCase<GetCouponsUseCaseInput, GetCouponsUseCaseOutput> {
}
