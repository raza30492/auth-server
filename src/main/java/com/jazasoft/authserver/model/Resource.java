package com.jazasoft.authserver.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Resource extends BaseEntity {

    @NotEmpty
    @Column(nullable = false)
    private String resourceId;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Column(name = "description")
    private String desc;

    @ManyToOne(optional = false)
    private App app;

    @JsonIgnore
    @OneToMany(
            mappedBy = "resource",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<RoleResource> roleList = new HashSet<>();

    public Resource() {
    }

    public Resource(String resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Set<RoleResource> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<RoleResource> roleList) {
        this.roleList = roleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Resource resource = (Resource) o;
        return Objects.equals(resourceId, resource.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resourceId);
    }
}
