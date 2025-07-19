package com.miguelsperle.nexbuy.core.domain.pagination;

public record PaginationMetadata(
        int currentPage,
        int perPage,
        int totalPages,
        long totalItems
) {
}
