package com.jazasoft.authserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Tenant extends BaseEntity {

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(name = "description")
    private String desc;

    @OneToMany(mappedBy = "tenant")
    private Set<User> userList;

}
