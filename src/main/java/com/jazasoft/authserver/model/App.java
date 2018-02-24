package com.jazasoft.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class App extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private String appId;

    @NotNull
    @Column(nullable = false)
    private String appName;

    @Column(name = "description")
    private String desc;

    @NotNull
    @Column(nullable = false)
    private String appPrefix;

    private String endpoint;

    @JsonIgnore
    @ManyToMany(mappedBy = "appList")
    private Set<User> userList;

    @JsonIgnore
    @OneToMany(mappedBy = "app",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roleList;

    @JsonIgnore
    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Permission> permissionList;

    public App() {
    }

    public App(String appId, String appName, String appPrefix) {
        this.appId = appId;
        this.appName = appName;
        this.appPrefix = appPrefix;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

    public Set<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(Set<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public String getAppPrefix() {
        return appPrefix;
    }

    public void setAppPrefix(String appPrefix) {
        this.appPrefix = appPrefix;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
