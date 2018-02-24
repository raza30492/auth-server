package com.jazasoft.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_resource_rel")
public class RoleResource {

    @EmbeddedId
    private RoleResourceId id = new RoleResourceId();

    @JsonIgnore
    @ManyToOne
    @MapsId("roleId")
    private Role role;

    @JsonIgnore
    @ManyToOne
    @MapsId("resourceId")
    private Resource resource;

    /**
     * Permissions on a resource: [read, write, update, delete, archive]
     */
    private String scopes;

    public RoleResource() {
    }

    public RoleResource(Role role, Resource resource) {
        this.role = role;
        this.resource = resource;
    }

    public RoleResource(Role role, Resource resource, String scopes) {
        this.role = role;
        this.resource = resource;
        this.scopes = scopes;
    }

    public RoleResourceId getId() {
        return id;
    }

    public void setId(RoleResourceId id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleResource that = (RoleResource) o;
        return Objects.equals(role, that.role) &&
                Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, resource);
    }
}
