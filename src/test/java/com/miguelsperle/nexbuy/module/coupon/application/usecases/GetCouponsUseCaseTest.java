package com.miguelsperle.nexbuy.module.coupon.application.usecases;

import com.miguelsperle.nexbuy.module.coupon.application.ports.out.persistence.CouponRepository;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs.GetCouponsUseCaseInput;
import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.GetCouponsUseCaseOutput;
import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.utils.CouponBuilderTest;
import com.miguelsperle.nexbuy.shared.domain.pagination.Pagination;
import com.miguelsperle.nexbuy.shared.domain.pagination.PaginationMetadata;
import com.miguelsperle.nexbuy.shared.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetCouponsUseCaseTest {
    @InjectMocks
    private GetCouponsUseCaseImpl getCouponsUseCase;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("Should get coupons")
    public void should_get_coupons() {
        final Coupon coupon = CouponBuilderTest.create(false);

        final SearchQuery searchQuery = SearchQuery.newSearchQuery(1, 10, "name", "asc");

        final PaginationMetadata paginationMetadata = new PaginationMetadata(
                searchQuery.page(), searchQuery.perPage(), 1, List.of(coupon).size()
        );

        final Pagination<Coupon> paginatedCoupons = new Pagination<>(
                paginationMetadata, List.of(coupon)
        );

        Mockito.when(this.couponRepository.findAllPaginated(Mockito.any())).thenReturn(paginatedCoupons);

        final GetCouponsUseCaseOutput getCouponsUseCaseOutput = this.getCouponsUseCase.execute(GetCouponsUseCaseInput.with(searchQuery));

        Assertions.assertNotNull(getCouponsUseCaseOutput);
        Assertions.assertNotNull(getCouponsUseCaseOutput.paginatedCoupons());

        Assertions.assertEquals(paginatedCoupons, getCouponsUseCaseOutput.paginatedCoupons());
        Assertions.assertEquals(1, getCouponsUseCaseOutput.paginatedCoupons().items().size());
        Assertions.assertEquals(paginationMetadata, getCouponsUseCaseOutput.paginatedCoupons().paginationMetadata());

        Mockito.verify(this.couponRepository, Mockito.times(1)).findAllPaginated(Mockito.any());
    }
}
