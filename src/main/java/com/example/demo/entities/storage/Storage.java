package com.example.demo.entities.storage;

import java.util.List;

import com.example.demo.entities.password.Password;
import com.example.demo.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@ToString(exclude = "passwords")
public class Storage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // number unique

    @Column(length = 1000)
    private String tag;

    @Column(length = 1000)
    private String title;

    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    private List<Password> passwords;
}
