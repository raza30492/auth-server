package com.jazasoft.authserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
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

    @ManyToOne
    //@JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    //@JoinColumn(name = "app_id")
    private App app;

}
