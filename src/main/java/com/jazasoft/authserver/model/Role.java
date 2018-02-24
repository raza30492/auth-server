package com.jazasoft.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
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

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<RoleResource> resourceList = new HashSet<>();

    public Role() {
    }

    public Role(String roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public void addResource(Resource resource, String scopes) {
        RoleResource roleResource = new RoleResource(this, resource, scopes);
        resourceList.add(roleResource);
        resource.getRoleList().add(roleResource);
    }

    public void removeResource(Resource resource) {
        for (Iterator<RoleResource> itr = resourceList.iterator(); itr.hasNext(); ) {
           RoleResource roleResource = itr.next();
           if (roleResource.getRole().equals(this) && roleResource.getResource().equals(resource)) {
               itr.remove();
               roleResource.setRole(null);
               roleResource.setResource(null);
               roleResource.getResource().getRoleList().remove(roleResource);
           }
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roleId);
    }
}
