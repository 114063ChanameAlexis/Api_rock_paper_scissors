package com.example.demo.services;

import com.example.demo.dtos.common.login.Credential;
import com.example.demo.dtos.common.login.CredentialV2;
import com.example.demo.models.Player;

public interface LoginService {
    Player login(Credential credential);
    Player login(CredentialV2 credential);
}
