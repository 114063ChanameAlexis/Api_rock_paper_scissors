package com.example.demo.dtos.common.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialV2 {
    @Schema(title = "Email or userName to logged in",
            description = "The player email or username",
            example = " user@gmail.com or username" ,
            nullable = false)
    @NotNull(message = "identity can't null")
    @JsonProperty("identity")
    private String identity;
    @NotNull(message = "password can't null")
    private String password;
}
