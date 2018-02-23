package com.jazasoft.authserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
public class License extends BaseEntity {

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private String productKey;

    @Column(nullable = false)
    private Date purchasedOn;

    private Date activatedOn;

    @Column(nullable = false)
    private Integer validity;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private String licenseType;

    @Column(nullable = false)
    private String licenseFlavour;

    @Column(nullable = false)
    private String entitlementType;

    @Column(nullable = false)
    private Long entitlement;

    @ManyToOne(optional = false)
    private Tenant tenant;

    @ManyToOne(optional = false)
    private App app;
}
