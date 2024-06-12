package com.example.demo.entities.password;

import com.example.demo.entities.storage.Storage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "password")
public class Password {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // number unique
    private String label;
    private String password;

    @ManyToOne
    private Storage storage;
}
