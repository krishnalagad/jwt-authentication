package com.jwt.authentication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@Table(name = "jwt_refreshToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshToken {

    // Refresh token itself is unique in nature so making it as primary key.

    @Id
    private String refreshToken;

    private Instant expiry;

    @OneToOne
    private User user;
}
