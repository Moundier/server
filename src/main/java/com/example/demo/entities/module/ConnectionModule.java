package com.example.demo.entities.module;

import java.security.Timestamp;
import java.util.Hashtable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionModule {
    
    private Long id;
    private String settings;
    private Boolean enabled;
    private Hashtable<Long, Long> moduleHash;
    private Hashtable<Long, Long> connectionHash;
    private Timestamp updatedAt;
    private Timestamp createdAt;

    public int moduleHash() {
        return this.moduleHash.hashCode();
    }

    public int connectionHash() {
        return this.connectionHash.hashCode();
    }
}
