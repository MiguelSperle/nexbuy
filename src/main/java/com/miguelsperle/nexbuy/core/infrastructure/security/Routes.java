package com.miguelsperle.nexbuy.core.infrastructure.security;

public class Routes {
    public static final String[] USER_MODULE_AUTHENTICATED_ENDPOINTS = {
            "/api/v1/users/information",
            "/api/v1/users/password",
            "/api/v1/users/me",
            "/api/v1/addresses",
            "/api/v1/addresses/{addressId}"
    };

    public static final String[] PRODUCT_MODULE_RESTRICTED_ENDPOINTS = {
            "/api/v1/admin/brands",
            "/api/v1/admin/brands/{brandId}",
            "/api/v1/admin/categories",
            "/api/v1/admin/categories/{categoryId}",
            "/api/v1/admin/colors",
            "/api/v1/admin/colors/{colorId}",
            "/api/v1/admin/products",
            "/api/v1/admin/products/{productId}",
            "/api/v1/admin/products/{productId}/status"
    };

    public static final String[] PRODUCT_MODULE_AUTHENTICATED_ENDPOINTS = {
            "/api/v1/brands",
            "/api/v1/categories",
            "/api/v1/brands/{brandId}",
            "/api/v1/categories/{categoryId}",
            "/api/v1/colors/{colorId}",
            "/api/v1/products",
            "/api/v1/products/{productId}"
    };
}
