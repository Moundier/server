package com.example.demo.entities.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    private Long id;
    private String name;
    private boolean enabled;
    private String slug;
}
