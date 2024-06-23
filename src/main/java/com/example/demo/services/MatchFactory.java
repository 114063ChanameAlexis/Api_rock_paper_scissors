package com.example.demo.services;

import com.example.demo.entities.MatchEntity;
import com.example.demo.entities.MatchRpsEntity;
import com.example.demo.models.Game;
import com.example.demo.models.Match;
import com.example.demo.models.MatchStatus;
import com.example.demo.models.Player;
import com.example.demo.models.rps.MatchRps;
import com.example.demo.models.rps.PlaysRps;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MatchFactory {

//    public static final Match createMatch(String gameCode){
//        switch (gameCode){
//            case "RPS":
//                return new MatchRps() {
//                };
//            default:
//                return new MatchRps();
//        }
//    }

    public static Match createMatch(Player player, Game game){
        switch (game.getCode()){
            case "RPS":
                return createMatchRps(player, game);
            default:
                return createMatchRps(player, game);
        }
    }

    public static Class <? extends Match> getTypesOfMatch(String gameCode) {
        switch (gameCode) {
            case "RPS":
                return MatchRps.class;
            default:
                return MatchRps.class;
        }
    }

    private static Match createMatchRps (Player player, Game game){
        MatchRps match = (MatchRps) getBasicMatch(player, game);
        match.setNumbers_of_plays(10);
        match.setRemainderPlays(10);
        match.setPlayer1Score(0);
        match.setPlayer2Score(0);
        match.setPlays(new ArrayList<PlaysRps>());
        return match;
    }

    private static Match getBasicMatch(Player player, Game game){
        Match match = getMatchInstance(game.getCode());
        match.setPlayer_id(player);
        match.setGame(game);
        match.setCreated_at(LocalDateTime.now());
        match.setStatus(MatchStatus.STARTED);
        return match;
    }

    public static Match getMatchInstance(String gameCode){
        switch (gameCode){
            case "RPS":
                return new MatchRps();
            default:
                return new MatchRps();
        }
    }
}
