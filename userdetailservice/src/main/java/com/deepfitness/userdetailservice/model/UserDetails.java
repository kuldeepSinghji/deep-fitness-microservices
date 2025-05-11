package com.deepfitness.userdetailservice.model;

import com.deepfitness.userdetailservice.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    @Enumerated(EnumType.STRING)
    private final UserRole userRole = UserRole.USER;
    private String gender;
    private Integer contactNumber;
    private String keyCloakId;
}
