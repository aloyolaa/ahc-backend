package com.petrotal.ahcbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "access_history")
@NoArgsConstructor
public class AccessHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date_access")
    private LocalDateTime dateAccess;

    @Column(name = "action")
    private String action;

    public AccessHistory(User user, String action) {
        this.user = user;
        this.action = action;
    }

    @PrePersist
    public void prePersist() {
        this.dateAccess = LocalDateTime.now();
    }
}