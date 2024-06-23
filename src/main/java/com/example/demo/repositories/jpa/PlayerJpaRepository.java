package com.example.demo.repositories.jpa;

import com.example.demo.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, Long>{
    Optional<PlayerEntity> findByUsernameOrEmail(String username, String email);
    Optional<PlayerEntity> findByUsernameAndPassword (String username, String password);
    Optional<PlayerEntity> findByEmailAndPassword (String email, String password);

    @Query("SELECT p FROM PlayerEntity p"+
            " WHERE (p.username Like :identity or p.email Like :identity) " +
            "AND p.password Like :password")
    Optional<PlayerEntity>findByUserNameOrEmailAndPassword(@Param("identity") String identity , @Param("password") String password );
}