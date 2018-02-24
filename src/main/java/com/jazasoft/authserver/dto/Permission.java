package com.jazasoft.authserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Permission {
    private String key;
    private String name;
    private String endpoint;
    private Long lastSync;

    public Permission() {
    }

    public Permission(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Permission(String key, String name, String endpoint, Long lastSync) {
        this.key = key;
        this.name = name;
        this.endpoint = endpoint;
        this.lastSync = lastSync;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getLastSync() {
        return lastSync;
    }

    public void setLastSync(Long lastSync) {
        this.lastSync = lastSync;
    }
}
