package com.jazasoft.authserver;

public class ApiUrls {
    public static final String OAUTH_URL = "/oauth/token";

    public static final String ROOT_URL_USERS = "/api/users";
    public static final String URL_USERS_USER = "/{userId}";
    public static final String URL_USERS_USER_COMPANY = "/{userId}/company";
    public static final String URL_USERS_USER_SEARCH_BY_EMAIL = "/search/byEmail";
    public static final String URL_USERS_FORGOT_PASSWORD = "/forgotPassword";

    public static final String ROOT_URL_PRODUCTS = "/api/products";
    public static final String URL_PRODUCTS_PRODUCT = "/{productId}";
    public static final String URL_PRODUCTS_FLAVOURS = "/flavours";
    public static final String URL_PRODUCTS_PRODUCT_SEARCH_BY_NAME = "/search/byName";

    public static final String ROOT_URL_CLIENTS = "/api/clients";
    public static final String URL_CLIENTS_CLIENT = "/{clientId}";
    public static final String URL_CLIENTS_CLIENT_SEARCH_BY_NAME = "/search/byName";

    public static final String ROOT_URL_LICENSES = "/api/licenses";
    public static final String URL_LICENSES_LICENSE = "/{licenseId}";
    public static final String URL_LICENSES_ACTIVATE = "/activate";
    public static final String URL_LICENSES_CHECK = "/check";
}
