package com.example.demo.services.impl;

import com.example.demo.dtos.common.play.PlayRpsDto;
import com.example.demo.entities.MatchEntity;
import com.example.demo.entities.MatchRpsEntity;
import com.example.demo.entities.PlayRpsEntity;
import com.example.demo.models.Match;
import com.example.demo.models.MatchStatus;
import com.example.demo.models.rps.MatchRps;
import com.example.demo.models.rps.PlaysRps;
import com.example.demo.models.rps.ShapeHand;
import com.example.demo.repositories.jpa.MatchJpaRepository;
import com.example.demo.repositories.jpa.PlayRpsJpaRepository;
import com.example.demo.services.PlayFactory;
import com.example.demo.services.PlayMatch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;


@Service
public class PlayMatchRpsImpl implements PlayMatch <PlaysRps, MatchRps> {

    @Autowired
    private PlayRpsJpaRepository playRpsJpaRepository;

    @Autowired
    private MatchJpaRepository matchJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Random random = new Random();

    @Override
    public PlaysRps play(PlaysRps playsRps, MatchRps matchRps) {

        playsRps.setMatchRpsId(matchRps.getId());
        if(Objects.isNull(playsRps.getShapeHandPlayer2())){
            playsRps.setShapeHandPlayer2(getRandomShapeHand());
        }
        evaluatePlay(playsRps, matchRps);
        calculatedMatchScore(playsRps, matchRps);
        calculatedMatchStatus(matchRps);
        matchRps.setUpdatedAt(LocalDateTime.now());
        PlayRpsEntity playRpsEntity = modelMapper.map(playsRps, PlayRpsEntity.class);
        playRpsJpaRepository.save(playRpsEntity);
        MatchEntity matchEntity = modelMapper.map(matchRps, MatchRpsEntity.class);
        matchJpaRepository.save(matchEntity);
        return playsRps;
    }

    private ShapeHand getRandomShapeHand(){
        Integer randomIndex = random.nextInt(3);
        return ShapeHand.values()[randomIndex];
    }

    private void evaluatePlay(PlaysRps playsRps, MatchRps matchRps){
        if(!isPlayTie(playsRps)){
            setWinner(playsRps, matchRps);
        }
    }

    private Boolean isPlayTie(PlaysRps playsRps){
        return playsRps.getShapeHandPlayer1().equals(playsRps.getShapeHandPlayer2());
    }

    private void setWinner (PlaysRps playsRps, MatchRps matchRps){
        if (playsRps.getShapeHandPlayer1().equals(ShapeHand.PAPER)){
            if(playsRps.getShapeHandPlayer2().equals(ShapeHand.ROCK)){
                playsRps.setWinnerId(matchRps.getPlayer_id().getId());
            }
            else {
                playsRps.setWinnerId(matchRps.getPlayer2_id().getId());
            }
        }

        else if (playsRps.getShapeHandPlayer1().equals(ShapeHand.ROCK)){
            if(playsRps.getShapeHandPlayer2().equals(ShapeHand.SCISSORS)){
                playsRps.setWinnerId(matchRps.getPlayer_id().getId());
            }
            else {
                playsRps.setWinnerId(matchRps.getPlayer2_id().getId());
            }
        }

        else {
            if(playsRps.getShapeHandPlayer2().equals(ShapeHand.PAPER)){
                playsRps.setWinnerId(matchRps.getPlayer_id().getId());
            }
            else {
                playsRps.setWinnerId(matchRps.getPlayer2_id().getId());
            }
        }
    }

    private void calculatedMatchScore (PlaysRps playsRps, MatchRps matchRps){
        if(Objects.nonNull(playsRps.getWinnerId())){
            if (playsRps.getWinnerId().equals(matchRps.getPlayer_id().getId())){
                matchRps.setPlayer1Score(matchRps.getPlayer1Score()+1);
            }
            else {
                matchRps.setPlayer2Score(matchRps.getPlayer2Score()+1);
            }
        }
    }
    private void calculatedMatchStatus (MatchRps matchRps){
        matchRps.setRemainderPlays(matchRps.getRemainderPlays()-1);
        if(matchRps.getRemainderPlays() ==0){
            matchRps.setStatus(MatchStatus.FINISHED);
            if (!isMatchTie(matchRps)){
                if (matchRps.getPlayer1Score()>matchRps.getPlayer2Score()){
                    matchRps.setWinnerId(matchRps.getPlayer_id().getId());
                }
                else {
                    matchRps.setWinnerId(matchRps.getPlayer2_id().getId());
                }
            }
        }
    }
    private boolean isMatchTie (MatchRps matchRps){
        return matchRps.getPlayer1Score().equals(matchRps.getPlayer2Score());
    }
}
