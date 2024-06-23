package com.example.demo.services.impl;

import com.example.demo.dtos.common.match.MatchDto;
import com.example.demo.dtos.common.play.PlayRequest;
import com.example.demo.entities.MatchEntity;
import com.example.demo.entities.MatchRpsEntity;
import com.example.demo.models.*;
import com.example.demo.repositories.jpa.MatchEntityFactory;
import com.example.demo.repositories.jpa.MatchJpaRepository;
import com.example.demo.services.*;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchJpaRepository matchJpaRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PlayStrategyFactory playStrategyFactory;

    @Override
    public List<Match> getMatchesByPlayer(Long playerId) {

        List<Match> matches = new ArrayList<>();
        Optional<List<MatchEntity>> optionalMatchEntities = matchJpaRepository.getAllByPlayerId(playerId);
        if(optionalMatchEntities.isPresent()){
            optionalMatchEntities.get().forEach(
                    me -> {matches.add(modelMapper.map(me, MatchFactory.getTypesOfMatch(me.getGame().getCode())));}
            );
        }
        System.out.println(matches);
        return matches;
    }

    @Override
    public Match createMatch(MatchDto matchDto) {

        Player player = playerService.getPlayerById(matchDto.getPlayerId());
        Game game = gameService.getGame(matchDto.getGameId());
        Match match = MatchFactory.createMatch(player, game);
        MatchEntity matchEntity = matchJpaRepository.save(modelMapper.map(match, MatchEntityFactory.getTypesOfMatch(match)));
        return modelMapper.map(matchEntity, match.getClass());
    }

    @Override
    public Match getMatchById(Long id) {
        MatchEntity me = (MatchEntity) Hibernate.unproxy(matchJpaRepository.getReferenceById(id));
        if(me != null){
            Match match = modelMapper.map(me,MatchFactory.getTypesOfMatch(me.getGame().getCode()));
            return match;
        }
        else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Override
    public Play play(Long matchId, PlayRequest playRequest) {
        Match match = this.getMatchById(matchId);
        if(match==null){
            throw new EntityNotFoundException();
        }
        if (match.getStatus()!=MatchStatus.STARTED){
            throw new ResponseStatusException(HttpStatus.CONFLICT
            , String.format("The match is %s", match.getStatus()));
        }
        Play play = PlayFactory.getPlayInstance(playRequest, match.getGame().getCode());
        PlayMatch playMatch = playStrategyFactory.getPlayStrategy(match.getGame().getCode());
        return playMatch.play(play,match);
    }
}
