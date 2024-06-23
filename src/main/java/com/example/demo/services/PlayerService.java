package com.example.demo.services;


import com.example.demo.models.Match;
import com.example.demo.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {

    Player getPlayerById (Long id);

    Player savePlayer (Player player);

    Player getPlayerByUserNameAndPassword (String userName, String password);

    Player getPlayerByEmailAndPassword (String email, String password);

    Player getPlayerByUserNameOrEmailAndPassword (String identity, String password);

}
