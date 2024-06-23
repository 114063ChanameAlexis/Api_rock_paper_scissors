package com.example.demo.repositores.jpa;


import com.example.demo.entities.PlayerEntity;
import com.example.demo.repositories.jpa.PlayerJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PlayerJpaRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Test
    public void findByUsernameOrEmailTest(){
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setEmail("Alexischaname20@gmail.com");
        playerEntity.setUsername("chana95");
        playerEntity.setPassword("Password03#");

        testEntityManager.persist(playerEntity);
        testEntityManager.flush();

        Optional<PlayerEntity> result = playerJpaRepository.findByUsernameOrEmail(
                "chana95","Alexischaname20@gmail.com");
        assertEquals("chana95", result.get().getUsername());
    }

}
