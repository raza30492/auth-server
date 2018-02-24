package com.jazasoft.authserver;

public class ApiUrls {
    public static final String OAUTH_URL = "/oauth/token";

    public static final String ROOT_URL_USERS = "/api/users";
    public static final String URL_USERS_USER = "/{userId}";
    public static final String URL_USERS_USER_SEARCH_BY_EMAIL = "/search/byEmail";

    public static final String ROOT_URL_APPS = "/api/apps";
    public static final String URL_APPS_APP = "/{appId}";
    public static final String URL_APPS_APP_SEARCH_BY_APP_ID = "/search/byAppId";

    public static final String ROOT_URL_TENANTS = "/api/tenants";
    public static final String URL_TENANTS_TENANT = "/{tenantId}";
    public static final String URL_TENANTS_TENANT_SEARCH_BY_TENANT_ID = "/search/byTenantId";

    public static final String ROOT_URL_ROLES = "/api/roles";
    public static final String URL_ROLES_ROLE = "/{roleId}";
    public static final String URL_ROLES_ROLE_SEARCH_BY_ROLE_ID = "/search/byRoleId";


    public static final String ROOT_URL_RESOURCES = "/api/resources";
    public static final String URL_RESOURCES_RESOURCE = "/{resourceId}";
    public static final String URL_RESOURCES_RESOURCE_SEARCH_BY_RESOURCE_ID = "/search/byResourceId";

    public static final String ROOT_URL_PERMISSIONS = "/api/permissions";
    public static final String URL_PERMISSIONS_PERMISSION = "/{permissionId}";

    public static final String ROOT_URL_LICENSES = "/api/licenses";
    public static final String URL_LICENSES_LICENSE = "/{licenseId}";
    public static final String URL_LICENSES_ACTIVATE = "/activate";
    public static final String URL_LICENSES_CHECK = "/check";
}
