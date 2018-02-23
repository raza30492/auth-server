package com.jazasoft.authserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class App extends BaseEntity {

    @Column(nullable = false)
    private String appId;

    @Column(nullable = false)
    private String appName;

    @Column(name = "description")
    private String desc;

    @ManyToMany(mappedBy = "appList")
    private Set<User> userList;

    @OneToMany(mappedBy = "app",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roleList;

    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Permission> permissionList;
}
