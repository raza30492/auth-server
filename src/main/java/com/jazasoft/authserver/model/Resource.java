package com.jazasoft.authserver.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Data
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

    @ManyToOne(optional = false)
    //@JoinColumn(name = "app_id")
    private App app;
}
