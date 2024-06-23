package com.example.demo.repositories.jpa;

import com.example.demo.entities.MatchEntity;
import com.example.demo.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchJpaRepository extends JpaRepository<MatchEntity, Long> {

    @Query("select m FROM MatchEntity m WHERE m.player_id.id = :playerId")
    Optional<List<MatchEntity>> getAllByPlayerId(Long playerId);

}
