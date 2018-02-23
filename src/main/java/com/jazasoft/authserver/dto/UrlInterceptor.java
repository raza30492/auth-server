package com.jazasoft.authserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UrlInterceptor {
    private String url;
    private List<Scope> scopes;

    public UrlInterceptor(String url) {
        this.url = url;
    }

    public UrlInterceptor(String url, List<Scope> scopes) {
        this.url = url;
        this.scopes = scopes;
    }
}
