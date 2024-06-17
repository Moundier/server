package com.example.demo.entities.storage;

import java.util.List;

import com.example.demo.entities.password.Password;
import com.example.demo.entities.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "storage")
@ToString
public class Storage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // number unique

    private String note;
    private String label;

    @OneToMany(mappedBy = "storage")
    private List<Password> passwords;

    @ManyToOne
    private User user;
}
