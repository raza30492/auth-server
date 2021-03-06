package com.jazasoft.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @NotEmpty
    @Column(nullable = false)
    private String firstName;

    @NotEmpty
    @Column(nullable = false)
    private String lastName;

    @NotEmpty
    @Column(nullable = false)
    private String username;

    @NotEmpty
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @NotEmpty
    @Column(nullable = false)
    private String mobile;

    /**
     * Whether acount was created for some fixed amount of time
     */
    @Column(nullable = false)
    private Boolean accountExpired;

    /**
     * Whether account is locked due to failed attempt to login
     */
    @Column(nullable = false)
    private Boolean accountLocked;

    /**
     * If user has not changed password for long time
     */
    @Column(nullable = false)
    private Boolean credentialExpired;

    @ManyToOne
    private Tenant tenant;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_rel",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roleList;

    @ManyToMany
    @JoinTable(
            name = "user_app_rel",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "app_id")
    )
    private Set<App> appList;

    public User() {
    }

    public User(String firstName, String lastName, String username, String email, String password, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.mobile = mobile;
        this.accountLocked = false;
        this.accountExpired = false;
        this.credentialExpired = false;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roleList != null) {
            List<GrantedAuthority> roles = new ArrayList<>();
            for (Role role: roleList) {
                roles.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleId()));
            }
            return roles;
        }
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpired != null && !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountLocked != null && !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialExpired != null && !credentialExpired;
    }

    /**
     * If user is active.
     * @return
     */
    @Override
    public boolean isEnabled() {
        if (enabled == null) return false;
        return enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getCredentialExpired() {
        return credentialExpired;
    }

    public void setCredentialExpired(Boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

    public Set<App> getAppList() {
        return appList;
    }

    public void setAppList(Set<App> appList) {
        this.appList = appList;
    }
}
