package com.example.demo.services;

import com.example.demo.models.Match;
import com.example.demo.models.rps.MatchRps;
import com.example.demo.models.rps.PlaysRps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayStrategyFactory {

    @Autowired
    private PlayMatch<PlaysRps, MatchRps> playMatchRps;

    public PlayMatch getPlayStrategy (String gameCode){
        switch (gameCode){
            case "RPS":
                return playMatchRps;
            default:
                return playMatchRps;
        }
    }

}
