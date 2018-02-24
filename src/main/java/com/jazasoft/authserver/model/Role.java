package com.jazasoft.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role extends BaseEntity {

    public static final String ROLE_MASTER = "master";
    public static final String ROLE_SUPER_USER = "super_user";
    public static final String ROLE_ADMIN = "admin";

    @Column(nullable = false)
    private String roleId;

    @Column(nullable = false)
    private String name;

    @Column(name = "description")
    private String desc;

    /**
     * Whether Role is Predefined for any Application or Custom
     */
    @Column(nullable = false)
    private Boolean isDefault;

    @JsonIgnore
    @ManyToOne
    private Tenant tenant;

    @JsonIgnore
    @ManyToOne
    private App app;

    @ManyToMany
    @JoinTable(
            name = "role_resource_rel",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private Set<Resource> resourceList;

    public Role() {
    }

    public static String getRoleMaster() {
        return ROLE_MASTER;
    }

    public static String getRoleSuperUser() {
        return ROLE_SUPER_USER;
    }

    public static String getRoleAdmin() {
        return ROLE_ADMIN;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Set<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(Set<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
