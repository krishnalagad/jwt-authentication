package com.jwt.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "jwt_refreshToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId;

    private String refreshToken;

    private Instant expiry;

    @OneToOne
    private User user;
}
