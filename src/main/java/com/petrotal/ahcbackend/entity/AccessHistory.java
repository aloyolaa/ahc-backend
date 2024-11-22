package com.petrotal.ahcbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    public AccessHistory(User user, LocalDateTime dateAccess) {
        this.user = user;
        this.dateAccess = dateAccess;
    }
}