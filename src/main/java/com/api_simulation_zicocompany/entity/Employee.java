package com.api_simulation_zicocompany.entity;

import java.time.Instant;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    private String name;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String publicKey;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String privateKey; // Ensure this is mapped as a large object

    @OneToMany(mappedBy = "employee")
    private Set<Receipt> receipts;

    Instant createdAt;
    Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}

