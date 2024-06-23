package com.example.demo.entities;

import com.example.demo.models.MatchStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name ="matches" )
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(name = "game_id")
    @ManyToOne
    private GameEntity game;

    @JoinColumn(name = "player_id")
    @ManyToOne
    private PlayerEntity player_id;

    @JoinColumn(name = "player2_id")
    @ManyToOne
    private PlayerEntity player2_id;

    @Column
    private LocalDateTime created_at;
    @Column
    private LocalDateTime updatedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private MatchStatus status;

}
