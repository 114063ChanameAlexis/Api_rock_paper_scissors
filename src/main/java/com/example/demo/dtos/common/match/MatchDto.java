package com.example.demo.dtos.common.match;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MatchDto {


    @NotNull
    private Long gameId;

    @NotNull
    private Long playerId;

}
