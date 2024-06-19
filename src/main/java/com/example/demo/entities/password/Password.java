package com.example.demo.entities.password;

import com.example.demo.entities.storage.Storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(length = 1000)
    private String tag;

    @Column(length = 1000)
    private String title;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;
}
