package com.jazasoft.authserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Scope {
    private String scope;
    private String method;
    private String roles;

    public Scope(String scope, String method, String roles) {
        this.scope = scope;
        this.method = method;
        this.roles = roles;
    }
}
