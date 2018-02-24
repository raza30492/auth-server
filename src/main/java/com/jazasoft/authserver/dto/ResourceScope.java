package com.jazasoft.authserver.dto;

import javax.validation.constraints.NotNull;

public class ResourceScope {

    @NotNull
    private Long id;

    @NotNull
    private String scope;

    public ResourceScope() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
