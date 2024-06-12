package com.example.demo.entities.storage;

import java.util.List;

import com.example.demo.entities.password.Password;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "storage")
public class Storage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // number unique

    private String note;
    private String label;

    @OneToMany(mappedBy = "storage")
    private List<Password> passwords;
}
