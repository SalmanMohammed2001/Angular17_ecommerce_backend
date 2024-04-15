package com.lk.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @TimeStamp 2024-02-14 13:26
 * @ProjectDetails RAPTOR-TASK-API
 * @Author udarasan
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName ="uid")
    private User uid;
}
