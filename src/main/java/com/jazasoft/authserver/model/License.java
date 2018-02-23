package com.jazasoft.authserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

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

    public License() {
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Date getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(Date purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseFlavour() {
        return licenseFlavour;
    }

    public void setLicenseFlavour(String licenseFlavour) {
        this.licenseFlavour = licenseFlavour;
    }

    public String getEntitlementType() {
        return entitlementType;
    }

    public void setEntitlementType(String entitlementType) {
        this.entitlementType = entitlementType;
    }

    public Long getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(Long entitlement) {
        this.entitlement = entitlement;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
