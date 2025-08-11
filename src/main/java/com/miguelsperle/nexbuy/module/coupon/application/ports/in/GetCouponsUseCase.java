package com.miguelsperle.nexbuy.module.coupon.application.ports.in;

import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.GetCouponsUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.GetCouponsUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;

public interface GetCouponsUseCase extends UseCase<GetCouponsUseCaseInput, GetCouponsUseCaseOutput> {
}
