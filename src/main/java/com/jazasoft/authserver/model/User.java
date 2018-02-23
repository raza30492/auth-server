package com.jazasoft.authserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private Boolean accountExpired;

    @Column(nullable = false)
    private Boolean accountLocked;

    @Column(nullable = false)
    private Boolean credentialExpired;

    @ManyToOne(cascade = CascadeType.ALL)
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
}
