package com.example.demo.services;

import com.example.demo.dtos.common.play.PlayRequest;
import com.example.demo.dtos.common.play.PlayRpsDto;
import com.example.demo.models.Play;
import com.example.demo.models.rps.PlaysRps;

public class PlayFactory {

    public static Play getPlayInstance(PlayRequest playRequest, String gameCode){
        switch (gameCode){
            case "RPS":
                return getPlayRpsInstance(playRequest);
            default:
                return getPlayRpsInstance(playRequest);
        }
    }

    private static Play getPlayRpsInstance (PlayRequest playRequest){
        PlayRpsDto playRpsDto = (PlayRpsDto) playRequest;
        PlaysRps playsRps = new PlaysRps();
        playsRps.setShapeHandPlayer1(playRpsDto.getShapeHandPlayer1());
        playsRps.setShapeHandPlayer2(playRpsDto.getShapeHandPlayer2());
        return playsRps;
    }


}
