package com.jazasoft.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    private Tenant tenant;

    @JsonIgnore
    @ManyToOne
    private App app;

}
