package com.jazasoft.authserver.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Resource extends BaseEntity {

    @Column(nullable = false)
    private String resourceId;

    @Column(nullable = false)
    private String resourceName;

    /**
     * Permissions on a resource: [read, write, update, delete, archive]
     */
    @Column(nullable = false)
    private String scope;

    @Column(name = "description")
    private String desc;

    @JsonIgnore
    @ManyToOne(optional = false)
    private App app;

    public Resource() {
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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
}
